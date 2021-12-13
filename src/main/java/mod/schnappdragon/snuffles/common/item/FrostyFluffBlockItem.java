package mod.schnappdragon.snuffles.common.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class FrostyFluffBlockItem extends BlockItem {
    public FrostyFluffBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    // Items in #wool and #carpets are automatically set as furnace fuel.
    // Overrides vanilla behavior and prevents frosty fluff from being used as fuel.
    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return 0;
    }
}