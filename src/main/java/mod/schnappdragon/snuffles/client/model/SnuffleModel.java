package mod.schnappdragon.snuffles.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.schnappdragon.snuffles.common.entity.animal.Snuffle;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

import java.util.Collections;

public class SnuffleModel<T extends Snuffle> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart tongue;
    private final ModelPart horns;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;

    public SnuffleModel(ModelPart part) {
        this.body = part.getChild("body");
        this.tongue = this.body.getChild("tongue");
        this.horns = this.body.getChild("horns");
        this.rightFrontLeg = part.getChild("right_front_leg");
        this.leftFrontLeg = part.getChild("left_front_leg");
        this.rightHindLeg = part.getChild("right_hind_leg");
        this.leftHindLeg = part.getChild("left_hind_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, -0.0436F, 0.0F, 0.0F));
        body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 25).addBox(-9.0F, -6.0F, -10.0F, 18.0F, 11.0F, 20.0F), PartPose.ZERO);
        body.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(80, 16).addBox(-6.0F, 0.0F, -7.0F, 12.0F, 1.0F, 8.0F), PartPose.offsetAndRotation(0.0F, 4.0F, -10.0F, 0.3927F, 0.0F, 0.0F));
        body.addOrReplaceChild("fluff", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, 5.0F, -10.0F, 18.0F, 5.0F, 20.0F), PartPose.ZERO);
        PartDefinition horns = body.addOrReplaceChild("horns", CubeListBuilder.create(), PartPose.ZERO);
        horns.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(102, 0).addBox(-3.0F, -7.0F, -6.0F, 3.0F, 8.0F, 8.0F), PartPose.offsetAndRotation(-9.0F, -4.0F, -6.0F, 0.0873F, 0.0F, 0.0F));
        horns.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(80, 0).addBox(0.0F, -7.0F, -6.0F, 3.0F, 8.0F, 8.0F), PartPose.offsetAndRotation(9.0F, -4.0F, -6.0F, 0.0873F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.5F, 0.0F, -2.5F, 5.0F, 7.0F, 5.0F), PartPose.offsetAndRotation(-4.5F, 17.0F, -5.5F, 0.0F, 0.1309F, 0.0F));
        partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 7.0F, 5.0F), PartPose.offsetAndRotation(4.5F, 17.0F, -5.5F, 0.0F, -0.1309F, 0.0F));
        partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.5F, 0.0F, -2.5F, 5.0F, 7.0F, 5.0F), PartPose.offsetAndRotation(-4.5F, 17.0F, 5.5F, 0.0F, 0.1309F, 0.0F));
        partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 7.0F, 5.0F), PartPose.offsetAndRotation(4.5F, 17.0F, 5.5F, 0.0F, -0.1309F, 0.0F));
        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    protected Iterable<ModelPart> headParts() {
        return Collections.emptyList();
    }

    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.rightFrontLeg, this.leftFrontLeg, this.rightHindLeg, this.leftHindLeg);
    }

    public void prepareMobModel(Snuffle snuffle, float limbSwing, float limbSwingAmount, float partialTick) {
        this.horns.visible = !snuffle.isBaby();
    }

    public void setupAnim(Snuffle snuffle, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.body.zRot = 0.12F * (snuffle.isFrosting() ?
                        Mth.sin(ageInTicks * 1.2F) :
                        Mth.sin(limbSwing * 0.5F + (float) Math.PI) * limbSwingAmount);

        this.rightHindLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftHindLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.rightFrontLeg.xRot = this.leftHindLeg.xRot;
        this.leftFrontLeg.xRot = this.rightHindLeg.xRot;

        this.tongue.xRot = 0.3927F + Mth.sin(ageInTicks * (snuffle.isLicking() || snuffle.isFrosting() ? 1.0F : 0.1F)) * 0.2F;
    }
}