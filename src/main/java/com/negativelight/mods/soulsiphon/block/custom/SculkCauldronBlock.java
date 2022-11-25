package com.negativelight.mods.soulsiphon.block.custom;

import com.negativelight.mods.soulsiphon.item.ModItems;
import com.negativelight.mods.soulsiphon.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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

public class SculkCauldronBlock extends Block {
    public static final BooleanProperty FULL = BooleanProperty.create("full");

    public SculkCauldronBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FULL, Boolean.FALSE));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offhandItem = player.getOffhandItem();
        ItemStack  stack = player.getItemInHand(hand);
        boolean success = false;
        if(state.getValue(FULL) &&  (mainHandItem.is(ModTags.Items.CAN_POSSES_TAG) || offhandItem.is(ModTags.Items.CAN_POSSES_TAG)))
        {
            Item stackItem;
            if(mainHandItem.is(ModTags.Items.CAN_POSSES_TAG)) {
                stackItem = mainHandItem.getItem();
                stack = mainHandItem;
                hand = InteractionHand.MAIN_HAND;
            } else {
                stackItem = offhandItem.getItem();
                stack = offhandItem;
                hand = InteractionHand.OFF_HAND;

            }
            Item changeItem;

            if (stackItem == ModItems.WEEPING_URN.get())
            {
                changeItem = ModItems.WEEPING_URN_FULL.get();
                swapItem(player, hand, stack, changeItem);

            } else if (stackItem == Items.TORCH) {
                changeItem = Items.SOUL_TORCH;
                swapItem(player, hand, stack, changeItem);

            }
            else if (stackItem == Items.CAMPFIRE)
            {
                changeItem = Items.SOUL_CAMPFIRE;
                swapItem(player, hand, stack, changeItem);

            } else if (stackItem == Items.LANTERN) {
                changeItem = Items.SOUL_LANTERN;
                swapItem(player, hand, stack, changeItem);

            }
            else if (stackItem == Items.GLASS_BOTTLE) {
                changeItem = Items.EXPERIENCE_BOTTLE;
                swapItem(player, hand, stack, changeItem);
            }


            success = true;
            level.setBlock(pos, state.setValue(FULL, false), 3);
        }
        else {
            return  InteractionResult.PASS;
        }

        //**UPDATE STATS
        if(!level.isClientSide() && success)
        {
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        }

        return InteractionResult.CONSUME;

    }

    private void swapItem(Player player, InteractionHand hand, ItemStack stack, Item changeItem) {
        stack.shrink(1);
        if(stack.isEmpty())
        {
            player.setItemInHand(hand, new ItemStack(changeItem));
        }
        else if(!player.getInventory().add(new ItemStack(changeItem)))
        {
            player.drop(new ItemStack(changeItem), false);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FULL);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_60584_) {

        return PushReaction.BLOCK;


    }

    public boolean canReceiveSoulDrip()
    {
        return true;
    }

}
