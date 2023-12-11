package mod.schnappdragon.snuffles.client.renderer;

import mod.schnappdragon.snuffles.client.model.*;
import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Snuffles.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SnufflesModelLayers {
    public static ModelLayerLocation SNUFFLE = new ModelLayerLocation(new ResourceLocation(Snuffles.MODID, "snuffle"), "main");

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SNUFFLE, SnuffleModel::createBodyLayer);
    }
}