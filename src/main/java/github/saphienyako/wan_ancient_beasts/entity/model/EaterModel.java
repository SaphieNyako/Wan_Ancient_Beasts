package github.saphienyako.wan_ancient_beasts.entity.model;

import github.saphienyako.wan_ancient_beasts.WanAncientBeastsMod;
import github.saphienyako.wan_ancient_beasts.entity.Eater;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class EaterModel extends DefaultedEntityGeoModel<Eater> {

    public EaterModel() {
        super(new ResourceLocation(WanAncientBeastsMod.MOD_ID, "eater"), true);
    }
}
