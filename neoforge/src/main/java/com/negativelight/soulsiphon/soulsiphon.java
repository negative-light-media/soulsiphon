package com.negativelight.soulsiphon;

import com.negativelight.soulsiphon.block.ModBlocks;
import com.negativelight.soulsiphon.item.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;



@Mod(Constants.MOD_ID)
public class soulsiphon {
    
    public soulsiphon(IEventBus eventBus) {
    
        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.

        // Use Forge to bootstrap the Common mod.
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();

        eventBus.addListener(this::addCreative);

    }

    @SubscribeEvent
    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        //Constants.LOG.info("Adding to Creative Mode Tab " + event.getTabKey());

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.WEEPING_URN.get());
            event.accept(ModItems.WEEPING_URN_FULL.get());
        }
        if(event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModBlocks.SCULK_CAULDRON.get());
            event.accept(ModBlocks.SOUL_SIPHON.get());
        }
    }

}