package mod.schnappdragon.snuffles.core.registry;

import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SnufflesParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Snuffles.MODID);

    public static final RegistryObject<SimpleParticleType> SNOWFLAKE = register("snowflake", false);

    private static RegistryObject<SimpleParticleType> register(String name, Boolean alwaysShow) {
        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(alwaysShow));
    }
}