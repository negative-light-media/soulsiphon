package com.negativelight.mods.soulsiphon;

import com.mojang.logging.LogUtils;
import com.negativelight.mods.soulsiphon.block.ModBlocks;
import com.negativelight.mods.soulsiphon.item.ModItems;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(soulsiphon.MODID)
public class soulsiphon {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "soulsiphon";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();


    public soulsiphon()
    {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }


    private void clientSetup(final FMLClientSetupEvent e){
    }

    private void setup(final FMLCommonSetupEvent event){
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }

    public void sendLogMsg(String msg)
    {
        LOGGER.info("[SOUL SIPHON] " + msg);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call

}
