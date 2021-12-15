package mod.schnappdragon.snuffles.core.tags;

import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public class SnufflesBlockTags {
    public static final Tag.Named<Block> SNUFFLES_SPAWNABLE_ON = makeTag("snuffles_spawnable_on");

    private static Tag.Named<Block> makeTag(String id) {
        return BlockTags.bind(Snuffles.MODID + ":" + id);
    }
}