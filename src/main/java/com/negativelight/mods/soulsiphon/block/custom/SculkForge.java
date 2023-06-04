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

public class SculkForge extends Block {
    public static final BooleanProperty FULL = BooleanProperty.create("fuelfull");

    /**
     * Constructior
     *
     * @param properties Block properties
     */
    public SculkForge(Properties properties) {
        super(properties);
        //****Register Block States
        this.registerDefaultState(this.stateDefinition.any().setValue(FULL, Boolean.FALSE));
    }

    /**
     * Handle Interactions with user
     * - Converts Possiable items into their possed form
     * Weeping Urn -> Full Weeping Urn
     * Torch -> Soul Torch
     * Campfire -> Soul Campfire
     * Lantern -> Soul Lantern
     * Glass Bottle -> Enchanted Bottle
     *
     * @param state  Inherited
     * @param level  Inherited
     * @param pos    Inherited
     * @param player Inherited
     * @param hand   Inherited
     * @param result Inherited
     * @return CONSUME if interaction was successful
     */
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        //**** Variables
        ItemStack mainHandItem = player.getMainHandItem(); // Players main hand item
        ItemStack offhandItem = player.getOffhandItem(); //Players offhand item
        ItemStack stack; //Item stack of interacted item
        Item stackItem; //Single Items from stack

        boolean success = false; //Pass/Fail bool
        //IF the Cauldron is FULL and one of the items has a CAN_POSSES_TAG
        if (state.getValue(FULL) && (mainHandItem.is(ModTags.Items.CAN_SMELT_TAG) || offhandItem.is(ModTags.Items.CAN_SMELT_TAG))) {
            //***Get interaction hand
            if (mainHandItem.is(ModTags.Items.CAN_SMELT_TAG)) {
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
            if (stackItem == Items.RAW_IRON) { //****TORCH
                changeItem = Items.IRON_INGOT;
                swapItem(player, hand, stack, changeItem);

            } else if (stackItem == Items.RAW_COPPER) //****CAMPFIRE
            {
                changeItem = Items.COPPER_INGOT;
                swapItem(player, hand, stack, changeItem);

            } else if (stackItem == Items.RAW_GOLD) { //****LANTERN
                changeItem = Items.GOLD_INGOT;
                swapItem(player, hand, stack, changeItem);

            } else if (stackItem == Items.RAW_COPPER_BLOCK) { //****GLASS BOTTLE
                changeItem = Items.COPPER_BLOCK;
                swapItem(player, hand, stack, changeItem);
            } else if (stackItem == Items.SAND) {
                changeItem = Items.GLASS;
                swapItem(player, hand, stack, changeItem);
            } else if (stackItem == Items.RED_SAND) {
                changeItem = Items.ORANGE_STAINED_GLASS;
                swapItem(player, hand, stack, changeItem);
            } else if (stackItem == Items.RAW_IRON_BLOCK) { //****GLASS BOTTLE
                changeItem = Items.IRON_BLOCK;
                swapItem(player, hand, stack, changeItem);
            } else if (stackItem == Items.RAW_GOLD_BLOCK) { //****GLASS BOTTLE
                changeItem = Items.GOLD_BLOCK;
                swapItem(player, hand, stack, changeItem);
            } else if (stackItem == Items.AMETHYST_SHARD) { //****GLASS BOTTLE
                changeItem = Items.TINTED_GLASS;
                swapItem(player, hand, stack, changeItem);
            } else if (stackItem == Items.ANCIENT_DEBRIS) { //****GLASS BOTTLE
                changeItem = Items.NETHERITE_SCRAP;
                swapItem(player, hand, stack, changeItem);
            } else if (stackItem == Items.STONE) { //****GLASS BOTTLE
                changeItem = Items.LAVA_BUCKET;
                swapItem(player, hand, stack, changeItem);
            } else if (stackItem == Items.SCULK) { //****GLASS BOTTLE
                changeItem = Items.END_STONE;
                swapItem(player, hand, stack, changeItem);
            } else if (stackItem == Items.STONE_BRICKS) { //****GLASS BOTTLE
                changeItem = Items.POLISHED_BLACKSTONE_BRICKS;
                swapItem(player, hand, stack, changeItem);
            } else if (stackItem == Items.POLISHED_BLACKSTONE_BRICKS) { //****GLASS BOTTLE
                changeItem = Items.GILDED_BLACKSTONE;
                swapItem(player, hand, stack, changeItem);
            }
            if (ModItems.WEEPING_URN_FULL.get() == stackItem){
                //do nothing
            }else{
                if (stack.getCount() >= 16){
                    player.setItemInHand(hand, new ItemStack(Items.AIR));
                    player.setItemInHand(hand, new ItemStack(stackItem,stack.getCount()-16));
                    success = true;
                    level.setBlock(pos, state.setValue(FULL, false), 3);
                }else{
                    player.setItemInHand(hand, new ItemStack(Items.AIR));
                    success = true;
                    level.setBlock(pos, state.setValue(FULL, false), 3);
                }

            }





        } else if ((!state.getValue(FULL)) && (mainHandItem.is(ModTags.Items.CAN_SMELT_TAG) || offhandItem.is(ModTags.Items.CAN_SMELT_TAG))) {
            if (mainHandItem.is(ModTags.Items.CAN_SMELT_TAG)) {
                stackItem = mainHandItem.getItem();
                stack = mainHandItem;
                hand = InteractionHand.MAIN_HAND;
            } else {
                stackItem = offhandItem.getItem();
                stack = offhandItem;
                hand = InteractionHand.OFF_HAND;
            }
            Item changeItem; //Item to change to
            stack.shrink(1);
            if (stackItem == ModItems.WEEPING_URN_FULL.get()) //****WEEPING URN
            {
                changeItem = ModItems.WEEPING_URN.get();
                if (state.getValue(FULL)){
                    success = false;

                }else{
                    swapItem(player, hand, stack, changeItem);
                    level.setBlock(pos, state.setValue(FULL, true), 3);
                    success = true;
                }

            }
            success = false;

        }else{
            return  InteractionResult.PASS;
        }

        //**UPDATE STATS
        if (!level.isClientSide() && success) {
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        }

        return InteractionResult.CONSUME;

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
        /**
        BlockPos blockPos;
        Level level;
        double d1 = (double)blockPos.getX() + 0.5D;
        double d2 = (double)((float)(blockPos.getY() + 1) - 0.6875F) - 0.0625D - 0.025D;
        double d3 = (double)blockPos.getZ() + 0.5D;

        ParticleOptions particleOptions = ParticleTypes.SCULK_SOUL;
        level.addParticle(particleOptions, d1, d2, d3, 0.0D, 0.0D, 0.0D);
         */
        if (stack.isEmpty()) //Check if the original stack is empty
        {
            if (changeItem == ModItems.WEEPING_URN.get()){
                player.setItemInHand(hand, new ItemStack(changeItem,1));
            }else{
                if (stack.getCount() > 16){
                    player.setItemInHand(hand, new ItemStack(changeItem,16));
                }else if (stack.getCount() < 16 && stack.getCount() != 0){
                    player.setItemInHand(hand, new ItemStack(changeItem,stack.getCount()));
                }

        }

        } else if (changeItem != ModItems.WEEPING_URN.get()&& (stack.getCount() >= 16) && player.getInventory().add(new ItemStack(changeItem,16))) //Otherwise add item in player inventory
        {


        }else if (changeItem != ModItems.WEEPING_URN.get()&& (stack.getCount() < 16)){
            //if less than 16 it does nothing
        }else{
            player.addItem(new ItemStack(changeItem, 1));
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

    /**
     * Overite of Push Reaction set the Push reaction to BLOCK
     *
     * @param p_60584_
     * @return BLOCK
     */
    @Override
    public PushReaction getPistonPushReaction(BlockState p_60584_) {

        return PushReaction.BLOCK;


    }
}
