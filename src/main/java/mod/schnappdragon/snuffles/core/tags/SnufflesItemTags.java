package mod.schnappdragon.snuffles.core.tags;

import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;

public class SnufflesItemTags {
    public static final Tag.Named<Item> SNUFFLE_FOOD = makeTag("snuffle_food");

    private static Tag.Named<Item> makeTag(String id) {
        return ItemTags.bind(Snuffles.MODID + ":" + id);
    }
}