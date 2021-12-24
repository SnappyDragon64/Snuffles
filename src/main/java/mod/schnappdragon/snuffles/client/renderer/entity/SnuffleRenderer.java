package mod.schnappdragon.snuffles.client.renderer.entity;

import mod.schnappdragon.snuffles.client.model.SnuffleModel;
import mod.schnappdragon.snuffles.client.renderer.SnufflesModelLayers;
import mod.schnappdragon.snuffles.client.renderer.entity.layers.SnuffleFluffLayer;
import mod.schnappdragon.snuffles.common.entity.animal.Snuffle;
import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SnuffleRenderer extends MobRenderer<Snuffle, SnuffleModel<Snuffle>> {
    private static final ResourceLocation[] SNUFFLE_LOCATIONS = {
            new ResourceLocation(Snuffles.MODID, "textures/entity/snuffle/snuffle_default.png"),
            new ResourceLocation(Snuffles.MODID, "textures/entity/snuffle/snuffle_sheepdog.png"),
            new ResourceLocation(Snuffles.MODID, "textures/entity/snuffle/snuffle_poro.png"),
            new ResourceLocation(Snuffles.MODID, "textures/entity/snuffle/snuffle_horseshoe.png")
    };
    private static final ResourceLocation[] FROSTY_LOCATIONS = {
            new ResourceLocation(Snuffles.MODID, "textures/entity/snuffle/frosty_default.png"),
            new ResourceLocation(Snuffles.MODID, "textures/entity/snuffle/frosty_sheepdog.png"),
            new ResourceLocation(Snuffles.MODID, "textures/entity/snuffle/frosty_poro.png"),
            new ResourceLocation(Snuffles.MODID, "textures/entity/snuffle/frosty_horseshoe.png")
    };

    public SnuffleRenderer(EntityRendererProvider.Context context) {
        super(context, new SnuffleModel<>(context.bakeLayer(SnufflesModelLayers.SNUFFLE)), 0.7F);
        this.addLayer(new SnuffleFluffLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Snuffle snuffle) {
        int i = snuffle.getHairstyle();
        return snuffle.isFrosty() ? FROSTY_LOCATIONS[i] : SNUFFLE_LOCATIONS[i];
    }
}