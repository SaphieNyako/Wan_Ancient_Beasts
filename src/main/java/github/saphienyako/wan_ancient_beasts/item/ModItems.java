package github.saphienyako.wan_ancient_beasts.item;

import github.saphienyako.wan_ancient_beasts.WanAncientBeastsMod;
import github.saphienyako.wan_ancient_beasts.entity.ModEntities;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WanAncientBeastsMod.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WanAncientBeastsMod.MOD_ID);

    public static final RegistryObject<ForgeSpawnEggItem> EATER_SPAWN_EGG = ITEMS.register("eater_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.EATER, 0xf085a9, 0xa1db67, new Item.Properties()));

    public static final RegistryObject<CreativeModeTab> WAN_ANCIENT_BEAST_TAB = TABS.register("spawn_eggs", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + WanAncientBeastsMod.MOD_ID + ".spawn_eggs"))
            .withTabsImage(new ResourceLocation(WanAncientBeastsMod.MOD_ID, "textures/gui/tab_icon.png"))
            .displayItems((enabledFeatures, entries) -> {
                entries.accept(ModItems.EATER_SPAWN_EGG.get());
            })
            .build());
}
