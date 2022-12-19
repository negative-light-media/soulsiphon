package com.negativelight.mods.soulsiphon.util;


import com.negativelight.mods.soulsiphon.soulsiphon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;


public class ModTags {
    public static class Blocks{
        public static final TagKey<Block> SOUL_BLOCK_TAG = BlockTags.create(new ResourceLocation(soulsiphon.MODID, "soul_block"));
        public static final TagKey<Block> CAN_POSSES_BLOCK_TAG = BlockTags.create(new ResourceLocation(soulsiphon.MODID, "possessable_block"));
    }

    public static class Items {
        public static final TagKey<Item> CAN_POSSES_TAG = ItemTags.create(new ResourceLocation(soulsiphon.MODID, "possessable"));


    }
}
