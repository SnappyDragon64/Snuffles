package mod.schnappdragon.snuffles.client.renderer.entity;

import mod.schnappdragon.snuffles.client.model.SnuffleModel;
import mod.schnappdragon.snuffles.client.renderer.SnufflesModelLayers;
import mod.schnappdragon.snuffles.common.entity.animal.Snuffle;
import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SnuffleRenderer extends MobRenderer<Snuffle, SnuffleModel<Snuffle>> {
    private static final ResourceLocation SNUFFLE_TEXTURES = new ResourceLocation(Snuffles.MODID, "textures/entity/snuffle/snuffle.png");
    private static final ResourceLocation FROSTY_TEXTURES = new ResourceLocation(Snuffles.MODID, "textures/entity/snuffle/frosty.png");

    public SnuffleRenderer(EntityRendererProvider.Context context) {
        super(context, new SnuffleModel<>(context.bakeLayer(SnufflesModelLayers.SNUFFLE)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(Snuffle snuffle) {
        return snuffle.isFrosty() ? FROSTY_TEXTURES : SNUFFLE_TEXTURES;
    }
}