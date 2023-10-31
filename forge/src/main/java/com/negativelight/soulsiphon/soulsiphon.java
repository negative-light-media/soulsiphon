package com.negativelight.soulsiphon;

import com.negativelight.soulsiphon.block.ModBlocks;
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

        eventBus.addListener(this::buildContents);
        //MinecraftForge.EVENT_BUS.addListener(this::onItemTooltip);
    }

    public void buildContents(BuildCreativeModeTabContentsEvent event) {
        //Constants.LOG.info("Adding to Creative Mode Tab " + event.getTabKey());
        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            //event.accept(ModBlocks.STONEWORK_BLOCK);
            //event.accept(ModBlocks.INFUSED_STONEWORK_BLOCK);
            //event.accept(ModBlocks.REINFORCED_STONEWORK_BLOCK);
        }

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            //event.accept(ModItems.GRADER_TOOL);
        }
    }

}