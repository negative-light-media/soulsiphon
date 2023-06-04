package com.negativelight.mods.soulsiphon.block.custom;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import com.negativelight.mods.soulsiphon.block.ModBlocks;
import com.negativelight.mods.soulsiphon.item.ModItems;
import com.negativelight.mods.soulsiphon.util.ModTags;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.*;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import com.negativelight.mods.soulsiphon.block.ModBlocks;
import com.negativelight.mods.soulsiphon.item.ModItems;
import com.negativelight.mods.soulsiphon.util.ModTags;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.*;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraftforge.client.ForgeHooksClient;
public class SoulLight extends Block {
    public static final BooleanProperty LIGHTON = BooleanProperty.create("lighton");
    public SoulLight(Properties properties) {
        super(properties);
        //****Register Block States
        this.registerDefaultState(this.stateDefinition.any().setValue(LIGHTON, Boolean.FALSE));

    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(LIGHTON);
    }
    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos pos_2, boolean Boolean) {
        if (redstoneIsActivated(world, pos)) {
            onRedstoneActivated(world, pos, block, Boolean,state);
        }else{
            world.setBlock(pos, state.setValue(LIGHTON, false), 3);
        }
    }
    //if redstone signal is activated
    private boolean redstoneIsActivated(Level world, BlockPos pos) {
        if (world.hasNeighborSignal(pos)) {
            return true;
        }
        return false;
    }
    protected static final void onRedstoneActivated (Level world, BlockPos pos, Block block, boolean Boolean, BlockState state) {
        world.setBlock(pos, state.setValue(LIGHTON, true), 3);
    }

}
