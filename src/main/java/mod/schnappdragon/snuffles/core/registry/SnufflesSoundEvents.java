package mod.schnappdragon.snuffles.core.registry;

import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class SnufflesSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, Snuffles.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> FROSTY_FLUFF_THAW = register("block.frosty_fluff.thaw");

    public static final DeferredHolder<SoundEvent, SoundEvent> SNUFFLE_AMBIENT = register("entity.snuffle.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> SNUFFLE_DEATH = register("entity.snuffle.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> SNUFFLE_EAT = register("entity.snuffle.eat");
    public static final DeferredHolder<SoundEvent, SoundEvent> SNUFFLE_HURT = register("entity.snuffle.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> SNUFFLE_SHAKE = register("entity.snuffle.shake");
    public static final DeferredHolder<SoundEvent, SoundEvent> SNUFFLE_SHEAR = register("entity.snuffle.shear");
    public static final DeferredHolder<SoundEvent, SoundEvent> SNUFFLE_STEP = register("entity.snuffle.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> SNUFFLE_STYLE = register("entity.snuffle.style");
    public static final DeferredHolder<SoundEvent, SoundEvent> SNUFFLE_THAW = register("entity.snuffle.thaw");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Snuffles.MODID, name)));
    }
}