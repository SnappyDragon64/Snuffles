package mod.schnappdragon.snuffles.client.renderer;

import mod.schnappdragon.snuffles.client.renderer.entity.SnuffleRenderer;
import mod.schnappdragon.snuffles.core.Snuffles;
import mod.schnappdragon.snuffles.core.registry.SnufflesEntityTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = Snuffles.MODID, bus = EventBusSubscriber.Bus.MOD)
public class SnufflesRenderers {
    @SubscribeEvent
    public static void rendererSetup(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(SnufflesEntityTypes.SNUFFLE.get(), SnuffleRenderer::new);
    }
}