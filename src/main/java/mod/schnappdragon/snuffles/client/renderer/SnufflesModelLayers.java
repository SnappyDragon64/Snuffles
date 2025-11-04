package mod.schnappdragon.snuffles.client.renderer;

import mod.schnappdragon.snuffles.client.model.*;
import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Snuffles.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SnufflesModelLayers {
    public static ModelLayerLocation SNUFFLE = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Snuffles.MODID, "snuffle"), "main");
    public static ModelLayerLocation SNUFFLE_SADDLE = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Snuffles.MODID, "snuffle"), "saddle");

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SNUFFLE, SnuffleModel::createBodyLayer);
        event.registerLayerDefinition(SNUFFLE_SADDLE, SnuffleModel::createBodyLayer);
    }
}