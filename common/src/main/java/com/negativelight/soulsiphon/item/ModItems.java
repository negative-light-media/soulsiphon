package com.negativelight.soulsiphon.item;

import com.negativelight.soulsiphon.Constants;
import com.negativelight.soulsiphon.item.custom.WeepingUrnItem;
import com.negativelight.soulsiphon.registration.RegistryObject;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

import static com.negativelight.soulsiphon.Constants.ITEMS;

public class ModItems {

    public static void loadClass() {}

    public static final RegistryObject<WeepingUrnItem> WEEPING_URN = registerItem(
         "weeping_urn", ()-> new WeepingUrnItem(
                 new Item.Properties().stacksTo(64).requiredFeatures()
            )
    );

    public static final RegistryObject<WeepingUrnItem> WEEPING_URN_FULL =
            registerItem("weeping_urn_full",
                    ()-> new WeepingUrnItem(
                            new Item.Properties()
                                    //.tab(CreativeModeTab.TAB_TOOLS)
                                    .stacksTo(16)
                                    .craftRemainder(WEEPING_URN.get())
                                    .requiredFeatures()
                    )
            );

    public static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> itemSupplier) {
        Constants.LOG.info("REGISTERING BLOCK AND ITEM " + name);
        //Items.DIRT
        return ITEMS.register(name, itemSupplier);
    }
}
