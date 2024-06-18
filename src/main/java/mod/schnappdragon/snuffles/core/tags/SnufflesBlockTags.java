package mod.schnappdragon.snuffles.core.tags;

import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class SnufflesBlockTags {
    public static final TagKey<Block> SNUFFLES_SPAWNABLE_ON = makeTag("snuffles_spawnable_on");

    private static TagKey<Block> makeTag(String id) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath(Snuffles.MODID, id));
    }
}