package com.negativelight.mods.soulsiphon.item;

import com.negativelight.mods.soulsiphon.item.custom.WeepingUrnItem;
import com.negativelight.mods.soulsiphon.soulsiphon;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, soulsiphon.MODID);

    public static final RegistryObject<WeepingUrnItem> WEEPING_URN =
            ITEMS.register("weeping_urn",
                    ()-> new WeepingUrnItem(
                            new Item.Properties()
                                    //.tab(CreativeModeTab.TAB_TOOLS)
                                    .stacksTo(64)
                    )
            );
    public static final RegistryObject<WeepingUrnItem> WEEPING_URN_FULL =
            ITEMS.register("weeping_urn_full",
                    ()-> new WeepingUrnItem(
                            new Item.Properties()
                                     //.tab(CreativeModeTab.TAB_TOOLS)
                                    .stacksTo(16)
                                    .craftRemainder(WEEPING_URN.get())
                    )
            );



    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }

}
