package mod.schnappdragon.snuffles.client.renderer;

import mod.schnappdragon.snuffles.client.model.*;
import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = Snuffles.MODID, bus = EventBusSubscriber.Bus.MOD)
public class SnufflesModelLayers {
    public static ModelLayerLocation SNUFFLE = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Snuffles.MODID, "snuffle"), "main");

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SNUFFLE, SnuffleModel::createBodyLayer);
    }
}