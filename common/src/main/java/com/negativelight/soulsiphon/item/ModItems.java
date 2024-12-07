package com.negativelight.soulsiphon.item;

import com.negativelight.soulsiphon.Constants;
import com.negativelight.soulsiphon.item.custom.WeepingUrnItem;
import com.negativelight.soulsiphon.registration.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.negativelight.soulsiphon.Constants.ITEMS;
import static com.negativelight.soulsiphon.Constants.MOD_ID;

public class ModItems {

    public static void loadClass() {}
    private  static ResourceKey<Item> vanillaItemId(String name) {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
    }
    public static final RegistryObject<WeepingUrnItem> WEEPING_URN = registerItem(
         "weeping_urn", WeepingUrnItem::new,
                 new Item.Properties().stacksTo(64).requiredFeatures().setId(vanillaItemId("weeping_urn"))

    );

    public static final RegistryObject<WeepingUrnItem> WEEPING_URN_FULL =
            registerItem("weeping_urn_full", WeepingUrnItem::new,
                            new Item.Properties()
                                    //.tab(CreativeModeTab.TAB_TOOLS)
                                    .stacksTo(16)
                                    .craftRemainder(WEEPING_URN.get())
                                    .requiredFeatures().setId(vanillaItemId("weeping_urn_full"))
            );





    public static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> itemSupplier) {
        Constants.LOG.info("REGISTERING ITEM " + name);
        return ITEMS.register(name, itemSupplier);
    }

    public  static <T extends Item> RegistryObject<T> registerItem(String name, Function<Item.Properties, Item> itemType, Item.Properties properties) {
        Item item = itemType.apply(properties.setId(vanillaItemId(name)));

        return (RegistryObject<T>) registerItem(name, ()-> item);
    }


}
