package mod.schnappdragon.snuffles.client.model;

import mod.schnappdragon.snuffles.common.entity.animal.Snuffle;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class SnuffleFluffModel<T extends Snuffle> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart fluff;

    public SnuffleFluffModel(ModelPart part) {
        this.root = part;
        this.fluff = part.getChild("fluff");
    }

    public static LayerDefinition createFluffLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("fluff", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, 5.0F, -10.0F, 18.0F, 5.0F, 20.0F), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, -0.0436F, 0.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    public ModelPart root() {
        return this.root;
    }

    public void setupAnim(Snuffle snuffle, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.fluff.zRot = 0.12F * (snuffle.isFrosting() ?
                Mth.sin(ageInTicks * 1.2F) :
                Mth.sin(limbSwing * 0.5F + (float) Math.PI) * limbSwingAmount);
    }
}