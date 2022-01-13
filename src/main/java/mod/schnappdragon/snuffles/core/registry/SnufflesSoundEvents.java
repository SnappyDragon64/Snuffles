package mod.schnappdragon.snuffles.core.registry;

import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SnufflesSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Snuffles.MODID);

    public static final RegistryObject<SoundEvent> SNUFFLE_AMBIENT = register("entity.snuffle.ambient");
    public static final RegistryObject<SoundEvent> SNUFFLE_DEATH = register("entity.snuffle.death");
    public static final RegistryObject<SoundEvent> SNUFFLE_EAT = register("entity.snuffle.eat");
    public static final RegistryObject<SoundEvent> SNUFFLE_HURT = register("entity.snuffle.hurt");
    public static final RegistryObject<SoundEvent> SNUFFLE_SHAKE = register("entity.snuffle.shake");
    public static final RegistryObject<SoundEvent> SNUFFLE_SHEAR = register("entity.snuffle.shear");
    public static final RegistryObject<SoundEvent> SNUFFLE_STEP = register("entity.snuffle.step");
    public static final RegistryObject<SoundEvent> SNUFFLE_STYLE = register("entity.snuffle.style");
    public static final RegistryObject<SoundEvent> SNUFFLE_THAW = register("entity.snuffle.thaw");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent((new ResourceLocation(Snuffles.MODID, name))));
    }
}