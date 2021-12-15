package mod.schnappdragon.snuffles.core.misc;

import mod.schnappdragon.snuffles.common.entity.animal.Snuffle;
import mod.schnappdragon.snuffles.core.registry.SnufflesEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class SnufflesSpawns {
    public static void registerSpawns() {
        registerSpawn(SnufflesEntityTypes.SNUFFLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Snuffle::checkSnuffleSpawnRules);
    }

    private static <T extends Mob> void registerSpawn(EntityType<T> entityType, SpawnPlacements.Type decoratorType, Heightmap.Types heightMapType, SpawnPlacements.SpawnPredicate<T> predicate) {
        SpawnPlacements.register(entityType, decoratorType, heightMapType, predicate);
    }
}
