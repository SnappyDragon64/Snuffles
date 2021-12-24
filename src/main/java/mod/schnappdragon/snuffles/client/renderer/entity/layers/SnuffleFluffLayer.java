package mod.schnappdragon.snuffles.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.schnappdragon.snuffles.client.model.SnuffleModel;
import mod.schnappdragon.snuffles.common.entity.animal.Snuffle;
import mod.schnappdragon.snuffles.core.Snuffles;
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

    public SnuffleFluffLayer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T snuffle, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (snuffle.hasFluff()) {
            VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(snuffle)));
            this.getParentModel().renderToBuffer(matrixStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(snuffle, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public ResourceLocation getTextureLocation(Snuffle snuffle) {
        Pair<ResourceLocation, ResourceLocation> fluff = snuffle.getHairstyle() == Snuffle.Hairstyle.HORSESHOE ? FLUFF_HORSESHOE : FLUFF;
        return snuffle.isFrosty() ? fluff.getRight() : fluff.getLeft();
    }
}