package mod.schnappdragon.snuffles.core.tags;

import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SnufflesItemTags {
    public static final TagKey<Item> SNUFFLE_FOOD = makeTag("snuffle_food");

    private static TagKey<Item> makeTag(String id) {
        return ItemTags.create(new ResourceLocation(Snuffles.MODID, id));
    }
}