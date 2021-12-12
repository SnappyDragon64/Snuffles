package mod.schnappdragon.snuffles.core.registry;

import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SnuffleItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Snuffles.MODID);

    public static final RegistryObject<Item> SNUFFLE_FLUFF = ITEMS.register("snuffle_fluff", () -> getBlockItem(SnufflesBlocks.SNUFFLE_FLUFF.get(), CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<Item> FROSTY_FLUFF = ITEMS.register("frosty_fluff", () -> getBlockItem(SnufflesBlocks.FROSTY_FLUFF.get(), CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<Item> SNUFFLE_FLUFF_CARPET = ITEMS.register("snuffle_fluff_carpet", () -> getBlockItem(SnufflesBlocks.SNUFFLE_FLUFF_CARPET.get(), CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<Item> FROSTY_FLUFF_CARPET = ITEMS.register("frosty_fluff_carpet", () -> getBlockItem(SnufflesBlocks.FROSTY_FLUFF_CARPET.get(), CreativeModeTab.TAB_BUILDING_BLOCKS));

    private static Item getBlockItem(Block block, CreativeModeTab tab) {
        return new BlockItem(block, (new Item.Properties()).tab(tab));
    }
}