package com.negativelight.mods.soulsiphon;
import com.negativelight.mods.soulsiphon.block.ModCreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import com.negativelight.mods.soulsiphon.block.ModBlocks;
import com.negativelight.mods.soulsiphon.block.custom.SoulSiphon;
import com.negativelight.mods.soulsiphon.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(soulsiphon.MODID)
public class soulsiphon {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "soulsiphon";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();


    public soulsiphon() {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        eventBus.addListener(this::addCreative);

    }

    private  void clientSetup(final FMLClientSetupEvent event)
    {
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }
    private void addCreative(CreativeModeTabEvent.BuildContents event){
        if (event.getTab() == CreativeModeTabs.FUNCTIONAL_BLOCKS){//to be compatable with main branch
            event.accept(ModItems.WEEPING_URN);
            event.accept(ModItems.WEEPING_URN_FULL);
            event.accept(ModBlocks.SCULK_CAULDRON);
            event.accept(ModBlocks.SOUL_SIPHON);
        }
        if (event.getTab() == ModCreativeModeTabs.SOULSIPHON_TAB) {//plus version exclusive feature
            event.accept(ModBlocks.BLOCK_O_SOULS);
            event.accept(ModBlocks.SCULK_FORGE);
            event.accept(ModItems.WEEPING_URN);
            event.accept(ModItems.WEEPING_URN_FULL);
            event.accept(ModBlocks.SCULK_CAULDRON);
            event.accept(ModBlocks.SOUL_SIPHON);
        }

    }
    public void sendLogMsg(String msg)
    {
        LOGGER.info("[SOUL SIPHON] " + msg);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call


}
