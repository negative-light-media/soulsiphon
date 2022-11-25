package com.negativelight.mods.soulsiphon.block.custom;


import com.mojang.logging.LogUtils;
import com.negativelight.mods.soulsiphon.block.ModBlocks;
import com.negativelight.mods.soulsiphon.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.SculkCatalystBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class SoulSiphon extends Block implements Fallable {

    public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;

    private static final float DAMAGE_PER_FALL_DISTANCE = 1.0F;

    private static final VoxelShape TIP_SHAPE_UP = Block.box(5.00D, 0D, 5D, 11D,11D, 11D);
    private static final VoxelShape TIP_SHAPE_DOWN = Block.box(5D, 5D, 5D, 11D,16D, 11D);
    private static final VoxelShape REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

    private  static final float DRIP_THRESHOLD = 0.2F;




    public SoulSiphon(Properties properties) {
        super(properties);

        this.registerDefaultState(this.defaultBlockState().setValue(TIP_DIRECTION, Direction.UP));

    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float v) {
        if(state.getValue(TIP_DIRECTION) == Direction.UP)
        {
            entity.causeFallDamage(v + 2.0F, 2.0F, DamageSource.STALAGMITE);
        } else {
            super.fallOn(level, state, pos, entity, v);
        }

    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource source) {
       if(canDrip(state))
       {
           float f = source.nextFloat();

           if (!(f > DRIP_THRESHOLD))
           {

                if(canDrip(level, pos)) {
                    spawnDripParticle(level, pos);
                }

           }

       }

    }

    private boolean canDrip(Level level, BlockPos pos) {

        BlockPos evalBlockPos = pos.above();
        BlockState evalBlockState = level.getBlockState(evalBlockPos);
        if(evalBlockState.is(Blocks.CRYING_OBSIDIAN)) {
            evalBlockPos = evalBlockPos.above();
            evalBlockState = level.getBlockState(evalBlockPos);
            if (evalBlockState.is(ModTags.Blocks.SOUL_BLOCK_TAG)) {
                return  true;
            }

        }

        return false;
    }

    private boolean canDrip(BlockState state) {
        return state.getValue(TIP_DIRECTION) == Direction.DOWN;
    }

    private static void spawnDripParticle(Level level, BlockPos blockPos) {

        double d0 = 0.0625D;
        double d1 = (double)blockPos.getX() + 0.5D;
        double d2 = (double)((float)(blockPos.getY() + 1) - 0.6875F) - 0.0625D - 0.025D;
        double d3 = (double)blockPos.getZ() + 0.5D;

        ParticleOptions particleOptions = ParticleTypes.SCULK_SOUL;
        level.addParticle(particleOptions, d1, d2, d3, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource source) {
        if(!this.canSurvive(state, level, pos) && state.is(ModBlocks.SOUL_SIPHON.get()))
        {
            if(state.getValue(TIP_DIRECTION) == Direction.UP)
            {
                level.destroyBlock(pos, true);
            }
            else
            {
                spawnFallingStalactite(state, level, pos);
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
        return isValidSiphonPlacement(reader, pos, state.getValue(TIP_DIRECTION));
    }

    public static final float MIN_TRANSFER_THRESH = 0.05859375F;
    public static final float MAX_TRANSFER_THRESH = 0.17578125F;

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource source) {
       float x = source.nextFloat();
        if(x <= MAX_TRANSFER_THRESH || x <= MIN_TRANSFER_THRESH)
        {

            if(canDrip(level, pos)) {


                    BlockPos targetPos = pos.above(2);

                    BlockState targetState = level.getBlockState(targetPos);

                if (Blocks.SOUL_SAND.equals(targetState.getBlock())) {
                    changeBlock(level, pos, targetPos, targetState, Blocks.SAND.defaultBlockState());
                    possesBlock(level, pos);

                }
                else if (Blocks.SOUL_SOIL.equals(targetState.getBlock())) {
                    changeBlock(level, pos, targetPos, targetState, Blocks.RED_SAND.defaultBlockState());
                    possesBlock(level, pos);
                }

            }


        }

    }

    private void possesBlock(ServerLevel level, BlockPos pos) {
        BlockPos targetPos = findSoulVessel(level, pos);
        if(targetPos == null)
        {
            return;
        }
        BlockState targetState = level.getBlockState(targetPos);

        if(targetState.is(ModTags.Blocks.CAN_POSSES_BLOCK_TAG)) {
            if(targetState.is(Blocks.SAND)) {
                changeBlock(level, pos, targetPos, targetState, Blocks.SOUL_SAND.defaultBlockState());
            }
            else if (targetState.is(Blocks.RED_SAND)) {
                changeBlock(level, pos, targetPos, targetState, Blocks.SOUL_SAND.defaultBlockState());

            }
            else if (targetState.is(ModBlocks.SCULK_CAULDRON.get())) {
                if(!targetState.getValue(SculkCauldronBlock.FULL)) {

                    targetState.setValue(SculkCauldronBlock.FULL, Boolean.valueOf(true));

                    level.setBlock(targetPos, targetState.setValue(SculkCauldronBlock.FULL, true), 3);

                }

            }
            else if (targetState.is(Blocks.SCULK_CATALYST)) {
                SculkCatalystBlockEntity catalystBlockEntity = (SculkCatalystBlockEntity) level.getBlockEntity(targetPos);
                SculkSpreader spreader = catalystBlockEntity.getSculkSpreader();
                spreader.addCursors(new BlockPos(targetPos.offset(0, 0.5D, 0)), 10);
                SculkCatalystBlock.bloom(level, targetPos, targetState, level.getRandom());
            }
        }

    }

    private void changeBlock(ServerLevel level, BlockPos pos, BlockPos targetPos, BlockState targetState, BlockState changeState) {
        level.setBlockAndUpdate(targetPos, changeState);
        Block.pushEntitiesUp(targetState, changeState, level, targetPos);
        level.gameEvent(GameEvent.BLOCK_CHANGE, targetPos, GameEvent.Context.of(changeState));
        level.levelEvent(1504, pos, 0);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_60584_) {
        return PushReaction.DESTROY;
    }

    public static boolean isValidSiphonPlacement(LevelReader reader, BlockPos pos, Direction direction)
    {
        BlockPos blockPos = pos.relative(direction.getOpposite());
        BlockState blockState = reader.getBlockState(blockPos);
        return blockState.isFaceSturdy(reader, blockPos, direction);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
        LevelAccessor levelAccessor = placeContext.getLevel();
        BlockPos pos = placeContext.getClickedPos();
        Direction direction = placeContext.getNearestLookingVerticalDirection().getOpposite();
        Direction direction1 = calculatePlacementDirection(levelAccessor, pos, direction);

        if (((direction1 != Direction.UP) && direction1 != Direction.DOWN))
        {

            return null;
        }
        else
        {
            boolean flag = !placeContext.isSecondaryUseActive();
            return this.defaultBlockState().setValue(TIP_DIRECTION, direction1);
        }

    }

    @Nullable
    private Direction calculatePlacementDirection(LevelReader levelReader, BlockPos pos, Direction direction) {
        Direction direction1;
        LogUtils.getLogger().info(direction.toString());
        if(isValidSiphonPlacement(levelReader, pos, direction))
        {
            direction1 = direction;
        }
        else
        {
            if(!isValidSiphonPlacement(levelReader, pos, direction.getOpposite()))
            {
                return null;
            }

            direction1 = direction.getOpposite();
        }

        return direction1;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState p_60578_, BlockGetter p_60579_, BlockPos p_60580_) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext collisionContext) {
        VoxelShape returnValue = TIP_SHAPE_UP;
        if(state.getValue(TIP_DIRECTION) == Direction.UP)
        {
            returnValue = TIP_SHAPE_UP;
        }
        else if (state.getValue(TIP_DIRECTION) == Direction.DOWN)
        {
            returnValue = TIP_SHAPE_DOWN;
        }
        return  returnValue;
    }

    @Override
    public void onBrokenAfterFall(Level level, BlockPos pos, FallingBlockEntity fallingBlockEntity) {
        if (!fallingBlockEntity.isSilent())
        {
            level.levelEvent(1045, pos, 0);
        }
    }

    public DamageSource getFallDamageSource() {
        return DamageSource.FALLING_STALACTITE;
    }

    public Predicate<Entity> getHurtsEntitySelector() {
        return EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(EntitySelector.LIVING_ENTITY_STILL_ALIVE);
    }

    private static void spawnFallingStalactite(BlockState state, ServerLevel level, BlockPos pos) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();
        //level.setDayTime(1000);

        FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(level, blockpos$mutableblockpos, state);
        if (state.is(ModBlocks.SOUL_SIPHON.get())) {
            int i = Math.max(1 + pos.getY() - blockpos$mutableblockpos.getY(), 6);
            float f = 1.0F * (float)i;
            fallingblockentity.setHurtsEntities(f, 40);
        }

        blockpos$mutableblockpos.move(Direction.DOWN);



    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(TIP_DIRECTION);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState state, LevelAccessor levelAccessor, BlockPos pos, BlockPos pos1) {
        if (direction != Direction.UP && direction != Direction.DOWN)
        {
            return blockState;
        }
        else
        {
            Direction blockTipDirection = blockState.getValue(TIP_DIRECTION);

            if(blockTipDirection == Direction.DOWN && levelAccessor.getBlockTicks().hasScheduledTick(pos,this))
            {
                return  blockState;
            }
            else if (direction == blockTipDirection.getOpposite() && !this.canSurvive(blockState, levelAccessor, pos)) {
                if(blockTipDirection == Direction.DOWN)
                {
                    levelAccessor.scheduleTick(pos, this, 2);
                }
                else {
                    levelAccessor.scheduleTick(pos, this, 1);
                }

                return blockState;

            }
        }
        return super.updateShape(blockState, direction, state, levelAccessor, pos, pos1);
    }

    private static BlockPos findSoulVessel(Level level, BlockPos pos) {
        Predicate<BlockState> predicate = (blockState) -> {
            return blockState.is(ModTags.Blocks.CAN_POSSES_BLOCK_TAG);
        };
        BiPredicate<BlockPos, BlockState> biPredicate = (pos1, blockState) -> {
            return canDripThrough(level, pos1, blockState);
        };
        return findBlockVertical(level, pos, Direction.DOWN.getAxisDirection(), biPredicate, predicate, 11).orElse((BlockPos)null);
    }

    private static boolean canDripThrough(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        if (blockState.isAir()) {
            return true;
        } else if (blockState.isSolidRender(blockGetter, blockPos)) {
            return false;
        } else if (!blockState.getFluidState().isEmpty()) {
            return false;
        } else {
            VoxelShape voxelshape = blockState.getCollisionShape(blockGetter, blockPos);
            return !Shapes.joinIsNotEmpty(REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK, voxelshape, BooleanOp.AND);
        }
    }


    private static Optional<BlockPos> findBlockVertical(LevelAccessor levelAccessor, BlockPos blockPos, Direction.AxisDirection axisDirection, BiPredicate<BlockPos, BlockState> blockPosBlockStateBiPredicate, Predicate<BlockState> blockStatePredicate, int i1) {
        Direction direction = Direction.get(axisDirection, Direction.Axis.Y);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = blockPos.mutable();

        for(int i = 1; i < i1; ++i) {
            blockpos$mutableblockpos.move(direction);
            BlockState blockstate = levelAccessor.getBlockState(blockpos$mutableblockpos);
            if (blockStatePredicate.test(blockstate)) {
                return Optional.of(blockpos$mutableblockpos.immutable());
            }

            if (levelAccessor.isOutsideBuildHeight(blockpos$mutableblockpos.getY()) || !blockPosBlockStateBiPredicate.test(blockpos$mutableblockpos, blockstate)) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }


}
