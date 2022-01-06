package mod.schnappdragon.snuffles.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.schnappdragon.snuffles.client.model.SnuffleFluffModel;
import mod.schnappdragon.snuffles.client.model.SnuffleModel;
import mod.schnappdragon.snuffles.client.renderer.SnufflesModelLayers;
import mod.schnappdragon.snuffles.common.entity.animal.Snuffle;
import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;

public class SnuffleFluffLayer<T extends Snuffle, M extends SnuffleModel<T>> extends RenderLayer<T, M> {
    private static final Pair<ResourceLocation, ResourceLocation> FLUFF = Pair.of(
            new ResourceLocation(Snuffles.MODID, "textures/entity/snuffle/snuffle_fluff.png"),
            new ResourceLocation(Snuffles.MODID, "textures/entity/snuffle/frosty_fluff.png")
    );
    private static final Pair<ResourceLocation, ResourceLocation> FLUFF_HORSESHOE = Pair.of(
            new ResourceLocation(Snuffles.MODID, "textures/entity/snuffle/snuffle_fluff_horseshoe.png"),
            new ResourceLocation(Snuffles.MODID, "textures/entity/snuffle/frosty_fluff_horseshoe.png")
    );
    private static final Pair<ResourceLocation, ResourceLocation> STACHE_HORSESHOE = Pair.of(
            new ResourceLocation(Snuffles.MODID, "textures/entity/snuffle/snuffle_stache_horseshoe.png"),
            new ResourceLocation(Snuffles.MODID, "textures/entity/snuffle/frosty_stache_horseshoe.png")
    );
    private final SnuffleFluffModel<T> model;

    public SnuffleFluffLayer(RenderLayerParent<T, M> parent, EntityModelSet modelSet) {
        super(parent);
        this.model = new SnuffleFluffModel<>(modelSet.bakeLayer(SnufflesModelLayers.SNUFFLE_FLUFF));
    }

    public void render(PoseStack stack, MultiBufferSource buffer, int packedLight, T snuffle, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!snuffle.isBaby() && (snuffle.hasFluff() || snuffle.getHairstyle() == Snuffle.Hairstyle.HORSESHOE)) {
            if (snuffle.isInvisible()) {
                Minecraft minecraft = Minecraft.getInstance();
                boolean flag = minecraft.shouldEntityAppearGlowing(snuffle);
                if (flag) {
                    this.getParentModel().copyPropertiesTo(this.model);
                    this.model.prepareMobModel(snuffle, limbSwing, limbSwingAmount, partialTicks);
                    this.model.setupAnim(snuffle, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                    VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.outline(this.getTextureLocation(snuffle)));
                    this.model.renderToBuffer(stack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(snuffle, 0.0F), 0.0F, 0.0F, 0.0F, 1.0F);
                }
            } else
                coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, this.getTextureLocation(snuffle), stack, buffer, packedLight, snuffle, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, 1.0F, 1.0F, 1.0F);
        }
    }

    public ResourceLocation getTextureLocation(Snuffle snuffle) {
        Pair<ResourceLocation, ResourceLocation> fluff;
        if (snuffle.getHairstyle() == Snuffle.Hairstyle.HORSESHOE)
            fluff = snuffle.hasFluff() ? FLUFF_HORSESHOE : STACHE_HORSESHOE;
        else
            fluff = FLUFF;

        return snuffle.isFrosty() ? fluff.getRight() : fluff.getLeft();
    }
}