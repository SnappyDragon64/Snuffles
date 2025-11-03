package mod.schnappdragon.snuffles.client.particle;

import mod.schnappdragon.snuffles.core.Snuffles;
import mod.schnappdragon.snuffles.core.registry.SnufflesParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Snuffles.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SnufflesParticleProviders {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(SnufflesParticleTypes.SNOWFLAKE.get(), SnufflesSnowflakeParticle.Provider::new);
    }
}