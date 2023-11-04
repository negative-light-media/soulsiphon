package com.negativelight.soulsiphon;

import com.negativelight.soulsiphon.block.ModBlocks;
import com.negativelight.soulsiphon.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.CreativeModeTabs;

public class soulsiphon implements ModInitializer {

    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();


        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(soulsiphon::addBuildingBlocks);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(soulsiphon::addTools);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(soulsiphon::addFunctionalBlocks);

        //Custom Redner Layers
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SOUL_SIPHON.get(), RenderType.cutout());
    }

    private static void addBuildingBlocks(FabricItemGroupEntries entries) {

    }

    private static void addTools(FabricItemGroupEntries entries) {
       // entries.accept(ModItems.GRADER_TOOL.get());
        entries.accept(ModItems.WEEPING_URN.get());
        entries.accept(ModItems.WEEPING_URN_FULL.get());
    }

    private static void addFunctionalBlocks(FabricItemGroupEntries entries) {
        entries.accept(ModBlocks.SCULK_CAULDRON.get());
        entries.accept(ModBlocks.SOUL_SIPHON.get());

    }



}
