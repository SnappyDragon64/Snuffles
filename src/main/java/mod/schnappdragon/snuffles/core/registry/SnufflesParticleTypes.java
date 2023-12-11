package mod.schnappdragon.snuffles.core.registry;

import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SnufflesParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, Snuffles.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SNOWFLAKE = register("snowflake", false);

    private static DeferredHolder<ParticleType<?>, SimpleParticleType> register(String name, Boolean alwaysShow) {
        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(alwaysShow));
    }
}