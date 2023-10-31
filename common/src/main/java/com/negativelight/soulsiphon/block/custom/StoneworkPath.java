package com.negativelight.soulsiphon.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.state.BlockState;

public class StoneworkPath  extends DirtPathBlock {
    public StoneworkPath(Properties $$0) {
        super($$0);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext $$0) {
        return  this.defaultBlockState();
    }

    @Override
    public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {

    }

    @Override
    public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
        return $$0;
    }
    public static void loadClass() {}
}
