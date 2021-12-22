package mod.schnappdragon.snuffles.core.misc;

import mod.schnappdragon.snuffles.core.registry.SnufflesBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

public class SnufflesFlammables {
    public static void registerFlammables() {
        registerFlammable(SnufflesBlocks.SNUFFLE_FLUFF.get(), 30, 60);
        registerFlammable(SnufflesBlocks.SNUFFLE_FLUFF_CARPET.get(), 60, 20);
    }

    private static void registerFlammable(Block block, int flameOdds, int burnOdds) {
        ((FireBlock) Blocks.FIRE).setFlammable(block, flameOdds, burnOdds);
    }
}