package com.negativelight.mods.soulsiphon.block;
import com.negativelight.mods.soulsiphon.block.custom.SoulSiphon;
import com.negativelight.mods.soulsiphon.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import com.negativelight.mods.soulsiphon.soulsiphon;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.network.chat.Component;
import java.awt.*;

@Mod.EventBusSubscriber(modid = soulsiphon.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeModeTabs {
    public static CreativeModeTab SOULSIPHON_TAB;
    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event){
        SOULSIPHON_TAB = event.registerCreativeModeTab(new ResourceLocation(soulsiphon.MODID,"soulsiphon_tab"),
                builder -> builder.icon(() -> new ItemStack(ModItems.WEEPING_URN.get()))
                        .title(Component.translatable("creativemodetab.soulsiphon_tab")));
    }
}
