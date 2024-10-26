package com.negativelight.soulsiphon.util;



import com.negativelight.soulsiphon.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;


public class ModTags {
    public static class Blocks{
        public static final TagKey<Block> SOUL_BLOCK_TAG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "blocks/soul_block"));
        public static final TagKey<Block> CAN_POSSES_BLOCK_TAG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "blocks/possessable_block"));
    }

    public static class Items {
        public static final TagKey<Item> CAN_POSSES_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "possessable"));
    }

}
