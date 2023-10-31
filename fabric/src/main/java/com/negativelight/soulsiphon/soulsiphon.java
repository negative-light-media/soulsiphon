package com.negativelight.soulsiphon;

import com.negativelight.soulsiphon.block.ModBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
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
    }

    private static void addBuildingBlocks(FabricItemGroupEntries entries) {

    }

    private static void addTools(FabricItemGroupEntries entries) {
       // entries.accept(ModItems.GRADER_TOOL.get());
    }



}
