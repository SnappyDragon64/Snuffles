package mod.schnappdragon.snuffles.core.registry;

import mod.schnappdragon.snuffles.common.entity.animal.Snuffle;
import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

@Mod.EventBusSubscriber(modid = Snuffles.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SnufflesEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, Snuffles.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<Snuffle>> SNUFFLE = ENTITY_TYPES.register("snuffle", () -> EntityType.Builder.of(Snuffle::new, MobCategory.CREATURE).sized(1.2F, 1.2F).clientTrackingRange(8).build("snuffles:snuffle"));

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(SNUFFLE.get(), Snuffle.registerAttributes().build());
    }
}