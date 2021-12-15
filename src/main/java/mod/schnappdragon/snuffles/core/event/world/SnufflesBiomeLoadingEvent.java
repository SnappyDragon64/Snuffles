package mod.schnappdragon.snuffles.core.event.world;

import mod.schnappdragon.snuffles.core.Snuffles;
import mod.schnappdragon.snuffles.core.registry.SnufflesEntityTypes;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Snuffles.MODID)
public class SnufflesBiomeLoadingEvent {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void modifyBiomes(BiomeLoadingEvent event) {
        if (Biomes.SNOWY_PLAINS.location().equals(event.getName()))
            event.getSpawns().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(SnufflesEntityTypes.SNUFFLE.get(), 5, 4, 4));
    }
}