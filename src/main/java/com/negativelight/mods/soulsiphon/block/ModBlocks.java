package com.negativelight.mods.soulsiphon.block;
import com.negativelight.mods.soulsiphon.block.ModCreativeModeTabs;
import com.negativelight.mods.soulsiphon.block.custom.SculkCauldronBlock;
import com.negativelight.mods.soulsiphon.block.custom.SculkForge;
import com.negativelight.mods.soulsiphon.block.custom.SoulLight;
import com.negativelight.mods.soulsiphon.block.custom.SoulSiphon;
import com.negativelight.mods.soulsiphon.item.ModItems;
import com.negativelight.mods.soulsiphon.soulsiphon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.*;
import net.minecraft.world.item.CreativeModeTabs;
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
import net.minecraft.world.item.CreativeModeTab;
import java.util.function.Supplier;
import com.negativelight.mods.soulsiphon.soulsiphon;
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, soulsiphon.MODID);
    //*****************BLOCK CONSTANTS
    public static final float[] SCULK_CAULDRON_STRENGTH = {50.0F, 1200.0F};
    //*******************BLocks
    public static final float[] SCULK_FORGE_STRENGTH = {50.0F, 1200.0F};
    //*******************BLocks
    public static  final RegistryObject<Block> SCULK_CAULDRON = registerBlock(
            "sculk_cauldron",
            () -> new SculkCauldronBlock(BlockBehaviour.Properties
                    .of(Material.SCULK)
                    .requiresCorrectToolForDrops().lightLevel(state -> state.getValue(SculkCauldronBlock.FULL) ? 5 : 0)
                    .strength(SCULK_CAULDRON_STRENGTH[0], SCULK_CAULDRON_STRENGTH[1])
                    .sound(SoundType.SCULK)
            ),
            CreativeModeTabs.FUNCTIONAL_BLOCKS
    );

    public static  final RegistryObject<Block> SOUL_SIPHON = registerBlock(
            "soul_siphon",
            () -> new SoulSiphon(
                    BlockBehaviour.Properties.of(Material.SCULK, MaterialColor.TERRACOTTA_CYAN)
                            .noOcclusion()
                            .randomTicks()
                            .requiredFeatures()
            ),
            CreativeModeTabs.FUNCTIONAL_BLOCKS
    );
    public static  final RegistryObject<Block> BLOCK_O_SOULS = registerBlock(
            "block_o_souls",
            () -> new Block(
                    BlockBehaviour.Properties.of(Material.SCULK, MaterialColor.TERRACOTTA_CYAN)
                            .sound(SoundType.SCULK)
                            .requiredFeatures()
            ),
            ModCreativeModeTabs.SOULSIPHON_TAB
    );
    public static  final RegistryObject<Block> SCULK_FORGE = registerBlock(
            "sculk_forge",
            () -> new SculkForge(
                    BlockBehaviour.Properties.of(Material.SCULK, MaterialColor.TERRACOTTA_CYAN)
                            .requiresCorrectToolForDrops().lightLevel(state -> state.getValue(SculkForge.FULL) ? 5 : 0)
                            .strength(SCULK_FORGE_STRENGTH[0], SCULK_FORGE_STRENGTH[1])
                            .sound(SoundType.SCULK)
                            .requiredFeatures()
            ),
            ModCreativeModeTabs.SOULSIPHON_TAB
    );
    public static  final RegistryObject<Block> SOUL_LIGHT = registerBlock(
            "soul_light",
            () -> new SoulLight(
                    BlockBehaviour.Properties.of(Material.FROGLIGHT, MaterialColor.TERRACOTTA_CYAN)
                            .lightLevel(state -> state.getValue(SoulLight.LIGHTON) ? 16 : 0)
                            .sound(SoundType.SOUL_SAND)
                            .requiredFeatures()
            ),
            ModCreativeModeTabs.SOULSIPHON_TAB
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
                                .requiredFeatures()
                )
        );

    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}