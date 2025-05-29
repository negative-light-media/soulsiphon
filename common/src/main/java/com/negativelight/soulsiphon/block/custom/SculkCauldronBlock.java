package com.negativelight.soulsiphon.block.custom;

import com.negativelight.soulsiphon.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;


public class SculkCauldronBlock extends Block {
    public static final BooleanProperty FULL = BooleanProperty.create("full");

    /**
     * Constructor
     *
     * @param properties Block properties
     */
    public SculkCauldronBlock(Properties properties) {
        super(properties);
        //****Register Block States
        this.registerDefaultState(this.stateDefinition.any().setValue(FULL, Boolean.FALSE));
    }

    private static boolean isPossesable(ItemStack itemStack) {
        return itemStack.is(ModItems.WEEPING_URN.get()) ||
                itemStack.is(Items.TORCH) ||
                itemStack.is(Items.CAMPFIRE) ||
                itemStack.is(Items.LANTERN) ||
                itemStack.is(Items.GLASS_BOTTLE) ||
                itemStack.is(Items.SAND) ||
                itemStack.is(Items.RED_SAND)
                ;
    }

    /**
     * Handle Interactions with user
     * - Converts Possible items into their possed form
     * Weeping Urn -> Full Weeping Urn
     * Torch -> Soul Torch
     * Campfire -> Soul Campfire
     * Lantern -> Soul Lantern
     * Glass Bottle -> Enchanted Bottle
     *
     * @param itemStack Inherited
     * @param state     Inherited
     * @param level     Inherited
     * @param pos       Inherited
     * @param player    Inherited
     * @param hand      Inherited
     * @param result    Inherited
     * @return CONSUME if interaction was successful
     */

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {

        //IF the Cauldron is FULL and one of the items has a CAN_POSSES_TAG
        //***Get interaction hand
        Item changeItem; //Item to change to
        if (! isPossesable(itemStack) || ! state.getValue(FULL)) {
            //Item cannot be possesed Pass interactions result
            return hand == InteractionHand.MAIN_HAND && isPossesable(player.getItemInHand(InteractionHand.OFF_HAND)) && state.getValue(FULL)
                    ? InteractionResult.PASS
                    : InteractionResult.TRY_WITH_EMPTY_HAND;
        }


        //This is gross. Need to research how to make this a "crafting recipe"
        if (itemStack.is(ModItems.WEEPING_URN.get())) //****WEEPING URN
        {
            changeItem = ModItems.WEEPING_URN_FULL.get();
            swapItem(player, hand, itemStack, changeItem);

        }
        else if (itemStack.is(Items.TORCH)) { //****TORCH
            changeItem = Items.SOUL_TORCH;
            swapItem(player, hand, itemStack, changeItem);
        }
        else if (itemStack.is(Items.CAMPFIRE)) //****CAMPFIRE
        {
            changeItem = Items.SOUL_CAMPFIRE;
            swapItem(player, hand, itemStack, changeItem);
        }
        else if (itemStack.is(Items.LANTERN)) { //****LANTERN
            changeItem = Items.SOUL_LANTERN;
            swapItem(player, hand, itemStack, changeItem);
        }
        else if (itemStack.is(Items.GLASS_BOTTLE)) { //****GLASS BOTTLE
            changeItem = Items.EXPERIENCE_BOTTLE;
            swapItem(player, hand, itemStack, changeItem);
        }
        else if (itemStack.is(Items.SAND)) {
            changeItem = Items.SOUL_SAND;
            swapItem(player, hand, itemStack, changeItem);
        }
        else if (itemStack.is(Items.RED_SAND)) {
            changeItem = Items.SOUL_SOIL;
            swapItem(player, hand, itemStack, changeItem);
        }

        level.setBlock(pos, state.setValue(FULL, false), 3);
        return InteractionResult.SUCCESS;

    }

    /**
     * Swap players hand with a new item
     *
     * @param player     - Player that will be effected
     * @param hand       - interaction hand that handle the event
     * @param stack      - stack originating items
     * @param changeItem - Item to swap to
     */
    private void swapItem(Player player, InteractionHand hand, ItemStack stack, Item changeItem) {
        stack.shrink(1); //Reduce the items in the main stack
        if (stack.isEmpty()) //Check if the original stack is empty
        {
            player.setItemInHand(hand, new ItemStack(changeItem));
        }
        else if (! player.getInventory().add(new ItemStack(changeItem))) //Otherwise add item in player inventory
        {
            player.drop(new ItemStack(changeItem), false);
        }
    }

    /**
     * Finalize the block state definition
     *
     * @param stateBuilder - State builder to manage states
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FULL);
    }


}
