package mod.schnappdragon.snuffles.core.misc;

import mod.schnappdragon.snuffles.common.entity.animal.Snuffle;
import mod.schnappdragon.snuffles.core.registry.SnufflesEntityTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.levelgen.Heightmap;

public class SnufflesSpawns {
    public static void registerSpawns() {
        registerSpawn(SnufflesEntityTypes.SNUFFLE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Snuffle::checkSnuffleSpawnRules);
    }

    private static <T extends Mob> void registerSpawn(EntityType<T> entityType, SpawnPlacementTypes.ON_GROUND decoratorType, Heightmap.Types heightMapType, SpawnPlacements.SpawnPredicate<T> predicate) {
        SpawnPlacements.register(entityType, decoratorType, heightMapType, predicate);
    }
}
