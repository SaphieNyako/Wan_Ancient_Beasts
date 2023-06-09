package github.saphienyako.wan_ancient_beasts.entity.render;

import github.saphienyako.wan_ancient_beasts.entity.Eater;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.function.Supplier;

public class EaterRenderer<T extends Eater> extends GeoEntityRenderer<T> {

    public EaterRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> model) {
        super(renderManager, model);
    }

    public static <T extends Eater> EntityRendererProvider<T> create(Supplier<GeoModel<T>> modelProvider) {
        return manager -> new EaterRenderer<>(manager, modelProvider.get());
    }
}
