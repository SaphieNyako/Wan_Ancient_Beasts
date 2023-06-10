package github.saphienyako.wan_ancient_beasts.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import github.saphienyako.wan_ancient_beasts.entity.Eater;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
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

    @Override
    public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack, T animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
        if(animatable.isBaby()){
            poseStack.scale(0.4F, 0.4F, 0.4F);
        }
        super.scaleModelForRender(widthScale, heightScale, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
    }
}
