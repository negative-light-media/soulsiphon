package com.negativelight.mods.soulsiphon.block;

import com.negativelight.mods.soulsiphon.block.custom.SculkCauldronBlock;
import com.negativelight.mods.soulsiphon.block.custom.SoulSiphon;
import com.negativelight.mods.soulsiphon.item.ModItems;
import com.negativelight.mods.soulsiphon.soulsiphon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, soulsiphon.MODID);
    //*****************BLOCK CONSTANTS
    public static final float[] SCULK_CAULDRON_STRENGTH = {50.0F, 1200.0F};
    //*******************BLocks
    public static  final RegistryObject<Block> SCULK_CAULDRON = registerBlock(
            "sculk_cauldron",
            () -> new SculkCauldronBlock(BlockBehaviour.Properties
                    .of(Material.SCULK)
                    .requiresCorrectToolForDrops().lightLevel(state -> state.getValue(SculkCauldronBlock.FULL) ? 5 : 0)
                    .strength(SCULK_CAULDRON_STRENGTH[0], SCULK_CAULDRON_STRENGTH[1])
                    .sound(SoundType.SCULK)),
            CreativeModeTabs.FUNCTIONAL_BLOCKS
    );

    public static  final RegistryObject<Block> SOUL_SIPHON = registerBlock(
      "soul_siphon",
            () -> new SoulSiphon(
                    BlockBehaviour.Properties.of(Material.SCULK, MaterialColor.TERRACOTTA_CYAN)
                            .noOcclusion()
                            .randomTicks()
            ),
            CreativeModeTabs.FUNCTIONAL_BLOCKS
    );


    //*******************HELPER FUNCTIONS
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;

    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return ModItems.ITEMS.register(name,
                () -> new BlockItem(block.get(),
                                    new Item.Properties()
                )
        );

    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
