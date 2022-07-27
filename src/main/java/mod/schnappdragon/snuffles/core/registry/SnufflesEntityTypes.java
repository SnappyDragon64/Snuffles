package mod.schnappdragon.snuffles.core.registry;

import mod.schnappdragon.snuffles.common.entity.animal.Snuffle;
import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Snuffles.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SnufflesEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Snuffles.MODID);

    public static final RegistryObject<EntityType<Snuffle>> SNUFFLE = ENTITY_TYPES.register("snuffle", () -> EntityType.Builder.of(Snuffle::new, MobCategory.CREATURE).sized(1.2F, 1.0F).clientTrackingRange(8).build("snuffles:snuffle"));

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(SNUFFLE.get(), Snuffle.registerAttributes().build());
    }
}