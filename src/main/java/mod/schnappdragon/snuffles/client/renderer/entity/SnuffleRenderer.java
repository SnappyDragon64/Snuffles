package mod.schnappdragon.snuffles.client.renderer.entity;

import mod.schnappdragon.snuffles.client.model.SnuffleModel;
import mod.schnappdragon.snuffles.client.renderer.SnufflesModelLayers;
import mod.schnappdragon.snuffles.client.renderer.entity.layers.SnuffleFluffLayer;
import mod.schnappdragon.snuffles.common.entity.animal.Snuffle;
import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class SnuffleRenderer extends MobRenderer<Snuffle, SnuffleModel<Snuffle>> {
    private static final ResourceLocation[] SNUFFLE_LOCATIONS = {
            ResourceLocation.fromNamespaceAndPath(Snuffles.MODID, "textures/entity/snuffle/snuffle_default.png"),
            ResourceLocation.fromNamespaceAndPath(Snuffles.MODID, "textures/entity/snuffle/snuffle_sheepdog.png"),
            ResourceLocation.fromNamespaceAndPath(Snuffles.MODID, "textures/entity/snuffle/snuffle_poro.png"),
            ResourceLocation.fromNamespaceAndPath(Snuffles.MODID, "textures/entity/snuffle/snuffle_horseshoe.png")
    };
    private static final ResourceLocation[] FROSTY_LOCATIONS = {
            ResourceLocation.fromNamespaceAndPath(Snuffles.MODID, "textures/entity/snuffle/snuffle_frosty_default.png"),
            ResourceLocation.fromNamespaceAndPath(Snuffles.MODID, "textures/entity/snuffle/snuffle_frosty_sheepdog.png"),
            ResourceLocation.fromNamespaceAndPath(Snuffles.MODID, "textures/entity/snuffle/snuffle_frosty_poro.png"),
            ResourceLocation.fromNamespaceAndPath(Snuffles.MODID, "textures/entity/snuffle/snuffle_frosty_horseshoe.png")
    };

    public SnuffleRenderer(EntityRendererProvider.Context context) {
        super(context, new SnuffleModel<>(context.bakeLayer(SnufflesModelLayers.SNUFFLE)), 0.7F);
        this.addLayer(new SnuffleFluffLayer<>(this));
        this.addLayer(new SaddleLayer<>(this, new SnuffleModel<>(context.bakeLayer(SnufflesModelLayers.SNUFFLE_SADDLE)), ResourceLocation.fromNamespaceAndPath(Snuffles.MODID, "textures/entity/snuffle/snuffle_saddle.png")));
    }

    @Override
    public ResourceLocation getTextureLocation(Snuffle snuffle) {
        int i = snuffle.getHairstyleId();
        return snuffle.isFrosty() ? FROSTY_LOCATIONS[i] : SNUFFLE_LOCATIONS[i];
    }
}