package mod.schnappdragon.snuffles.core.registry;

import mod.schnappdragon.snuffles.common.item.FrostyFluffBlockItem;
import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SnuffleItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Snuffles.MODID);

    public static final RegistryObject<Item> SNUFFLE_FLUFF = ITEMS.register("snuffle_fluff", () -> new BlockItem(SnufflesBlocks.SNUFFLE_FLUFF.get(), (new Item.Properties()).tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<Item> FROSTY_FLUFF = ITEMS.register("frosty_fluff", () -> new FrostyFluffBlockItem(SnufflesBlocks.FROSTY_FLUFF.get(), (new Item.Properties()).tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<Item> SNUFFLE_FLUFF_CARPET = ITEMS.register("snuffle_fluff_carpet", () -> new BlockItem(SnufflesBlocks.SNUFFLE_FLUFF_CARPET.get(), (new Item.Properties()).tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<Item> FROSTY_FLUFF_CARPET = ITEMS.register("frosty_fluff_carpet", () -> new FrostyFluffBlockItem(SnufflesBlocks.FROSTY_FLUFF_CARPET.get(), (new Item.Properties()).tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
}