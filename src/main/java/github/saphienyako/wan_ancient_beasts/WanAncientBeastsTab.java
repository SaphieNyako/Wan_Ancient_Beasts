package github.saphienyako.wan_ancient_beasts;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class WanAncientBeastsTab extends CreativeModeTab{

    private final ResourceLocation texture;

    protected WanAncientBeastsTab(Builder builder) {
        super(builder);
        this.texture = new ResourceLocation(WanAncientBeastsMod.MOD_ID, "textures/gui/tab_icon.png");
    }

    @Nonnull
    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getIconItem() {
        // This one is used for display, but we handle display ourselves
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getTabsImage() {
        return texture;
    }
}
