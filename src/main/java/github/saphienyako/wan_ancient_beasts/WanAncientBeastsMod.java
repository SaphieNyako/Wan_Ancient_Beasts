package github.saphienyako.wan_ancient_beasts;

import github.saphienyako.wan_ancient_beasts.entity.Eater;
import github.saphienyako.wan_ancient_beasts.entity.ModEntities;
import github.saphienyako.wan_ancient_beasts.entity.model.EaterModel;
import github.saphienyako.wan_ancient_beasts.entity.render.EaterRenderer;
import github.saphienyako.wan_ancient_beasts.item.ModItems;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib.GeckoLib;

import javax.annotation.Nonnull;

@Mod(WanAncientBeastsMod.MOD_ID)
public class WanAncientBeastsMod {

    public static final String MOD_ID = "wan_ancient_beasts";
    private static WanAncientBeastsMod instance;

    public WanAncientBeastsMod(){

        instance = this;

        GeckoLib.initialize();

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::entityAttributes);

        ModItems.ITEMS.register(eventBus);
        ModItems.TABS.register(eventBus);
        ModEntities.ENTITIES.register(eventBus);
    }

    @Nonnull
    public static WanAncientBeastsMod getInstance(){
        return instance;
    }

    public final ResourceLocation resource(String path) {
        return new ResourceLocation(WanAncientBeastsMod.MOD_ID, path);
    }

    protected void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            SpawnPlacements.register(ModEntities.EATER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Eater::canSpawn);
        });
    }


    @OnlyIn(Dist.CLIENT)
    protected void clientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.EATER.get(), EaterRenderer.create(EaterModel::new) );
    }

    private void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.EATER.get(), Eater.getDefaultAttributes().build());
    }
}
