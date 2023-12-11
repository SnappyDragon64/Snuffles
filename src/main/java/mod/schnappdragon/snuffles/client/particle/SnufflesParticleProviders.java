package mod.schnappdragon.snuffles.client.particle;

import mod.schnappdragon.snuffles.core.Snuffles;
import mod.schnappdragon.snuffles.core.registry.SnufflesParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Snuffles.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SnufflesParticleProviders {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        registerProvider(SnufflesParticleTypes.SNOWFLAKE.get(), SnufflesSnowflakeParticle.Provider::new);
    }

    private static <T extends ParticleOptions> void registerProvider(ParticleType<T> particle, ParticleEngine.SpriteParticleRegistration<T> factory) {
        Minecraft.getInstance().particleEngine.register(particle, factory);
    }
}