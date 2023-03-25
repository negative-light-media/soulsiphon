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

    /**
     * Constructior
     * @param properties Block properties
     */
    public SculkCauldronBlock(Properties properties) {
        super(properties);
        //****Register Block States
        this.registerDefaultState(this.stateDefinition.any().setValue(FULL, Boolean.FALSE));
    }

    /**
     *  Handle Interactions with user
     *  - Converts Possiable items into their possed form
     *  Weeping Urn -> Full Weeping Urn
     *  Torch -> Soul Torch
     *  Campfire -> Soul Campfire
     *  Lantern -> Soul Lantern
     *  Glass Bottle -> Enchanted Bottle
     * @param state Inherited
     * @param level Inherited
     * @param pos Inherited
     * @param player Inherited
     * @param hand Inherited
     * @param result Inherited
     * @return CONSUME if interaction was successful
     */
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        //**** Variables
        ItemStack mainHandItem = player.getMainHandItem(); // Players main hand item
        ItemStack offhandItem = player.getOffhandItem(); //Players offhand item
        ItemStack  stack; //Item stack of interacted item
        Item stackItem; //Single Items from stack

        boolean success = false; //Pass/Fail bool

        //IF the Cauldron is FULL and one of the items has a CAN_POSSES_TAG
        if(state.getValue(FULL) &&  (mainHandItem.is(ModTags.Items.CAN_POSSES_TAG) || offhandItem.is(ModTags.Items.CAN_POSSES_TAG)))
        {
            //***Get interaction hand
            if(mainHandItem.is(ModTags.Items.CAN_POSSES_TAG)) {
                stackItem = mainHandItem.getItem();
                stack = mainHandItem;
                hand = InteractionHand.MAIN_HAND;
            } else {
                stackItem = offhandItem.getItem();
                stack = offhandItem;
                hand = InteractionHand.OFF_HAND;
            }
            Item changeItem; //Item to change to

            //Pick the item output
            if (stackItem == ModItems.WEEPING_URN.get()) //****WEEPING URN
            {
                changeItem = ModItems.WEEPING_URN_FULL.get();
                swapItem(player, hand, stack, changeItem);

            } else if (stackItem == Items.TORCH) { //****TORCH
                changeItem = Items.SOUL_TORCH;
                swapItem(player, hand, stack, changeItem);

            }
            else if (stackItem == Items.CAMPFIRE) //****CAMPFIRE
            {
                changeItem = Items.SOUL_CAMPFIRE;
                swapItem(player, hand, stack, changeItem);

            } else if (stackItem == Items.LANTERN) { //****LANTERN
                changeItem = Items.SOUL_LANTERN;
                swapItem(player, hand, stack, changeItem);

            }
            else if (stackItem == Items.GLASS_BOTTLE) { //****GLASS BOTTLE
                changeItem = Items.EXPERIENCE_BOTTLE;
                swapItem(player, hand, stack, changeItem);
            }
            else if (stackItem == Items.SAND) {
                changeItem = Items.SOUL_SAND;
                swapItem(player, hand, stack, changeItem);
            }
            else if (stackItem == Items.RED_SAND) {
                changeItem = Items.SOUL_SOIL;
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

    /**
     * Swap players hand with a new item
     * @param player - Player that will be effected
     * @param hand - interaction hand that handle the event
     * @param stack - stack originating items
     * @param changeItem - Item to swap to
     */
    private void swapItem(Player player, InteractionHand hand, ItemStack stack, Item changeItem) {
        stack.shrink(1); //Reduce the items in the main stack
        if(stack.isEmpty()) //Check if the original stack is empty
        {
            player.setItemInHand(hand, new ItemStack(changeItem));
        }
        else if(!player.getInventory().add(new ItemStack(changeItem))) //Otherwise add item in player inventory
        {
            player.drop(new ItemStack(changeItem), false);
        }
    }

    /**
     * Finalize the block state definition
     * @param stateBuilder - State builder to manage states
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FULL);
    }

    /**
     * Overite of Push Reaction set the Push reaction to BLOCK
     * @param p_60584_
     * @return BLOCK
     */
    @Override
    public PushReaction getPistonPushReaction(BlockState p_60584_) {

        return PushReaction.BLOCK;


    }

}
