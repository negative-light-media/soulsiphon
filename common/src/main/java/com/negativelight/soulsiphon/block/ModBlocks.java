package com.negativelight.soulsiphon.block;

import com.negativelight.soulsiphon.Constants;
import com.negativelight.soulsiphon.block.custom.SculkCauldronBlock;
import com.negativelight.soulsiphon.block.custom.SoulSiphon;
import com.negativelight.soulsiphon.block.custom.StoneworkPath;
import com.negativelight.soulsiphon.registration.RegistryObject;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

import static com.negativelight.soulsiphon.Constants.BLOCKS;
import static com.negativelight.soulsiphon.Constants.ITEMS;

public class ModBlocks {

    //***************************CONSTANTS
    public static final float[] SCULK_CAULDRON_STRENGTH = {50.0F, 1200.0F};

    public static final RegistryObject<Block> SCULK_CAULDRON = registerBlock(
            "sculk_cauldron",
            () -> new SculkCauldronBlock(BlockBehaviour.Properties.of()
                    .requiresCorrectToolForDrops()
                    .strength(SCULK_CAULDRON_STRENGTH[0], SCULK_CAULDRON_STRENGTH[1])
                    .sound(SoundType.SCULK)
                    .pushReaction(PushReaction.BLOCK))
    );


    public static  final RegistryObject<Block> SOUL_SIPHON = registerBlock(
            "soul_siphon",
            () -> new SoulSiphon(
                    BlockBehaviour.Properties.of()
                            .noOcclusion()
                            .randomTicks()
                            .requiredFeatures()
                            .pushReaction(PushReaction.DESTROY)
    ));





    //****************** UTILITY FUNCTIONS
    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> blockSupplier) {
        Constants.LOG.info("REGISTERING BLOCK AND ITEM " + name);
        RegistryObject<T> retVal = BLOCKS.register(name, blockSupplier);
        if (retVal == null)
        {
            Constants.LOG.error("Registry Object is NULL");
        }
        ITEMS.register(name, ()-> {
            assert retVal != null;
            return new BlockItem(retVal.get(), new Item.Properties());
        });
        //Items.DIRT
        return retVal;
    }

    public static void loadClass() {}
}
