package com.negativelight.soulsiphon;

import com.negativelight.soulsiphon.block.ModBlocks;
import com.negativelight.soulsiphon.item.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;



@Mod(Constants.MOD_ID)
public class soulsiphon {
    
    public soulsiphon() {
    
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Use Forge to bootstrap the Common mod.
        Constants.LOG.info("Hello Forge world!");
        CommonClass.init();

        eventBus.addListener(this::addCreative);

    }
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