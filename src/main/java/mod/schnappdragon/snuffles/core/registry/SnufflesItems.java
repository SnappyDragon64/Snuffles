package mod.schnappdragon.snuffles.core.registry;

import mod.schnappdragon.snuffles.common.item.FuelBlockItem;
import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SnufflesItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Snuffles.MODID);

    public static final RegistryObject<Item> SNUFFLE_FLUFF = ITEMS.register("snuffle_fluff", () -> new FuelBlockItem(SnufflesBlocks.SNUFFLE_FLUFF.get(), 100, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<Item> FROSTY_FLUFF = ITEMS.register("frosty_fluff", () -> new BlockItem(SnufflesBlocks.FROSTY_FLUFF.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<Item> SNUFFLE_FLUFF_CARPET = ITEMS.register("snuffle_fluff_carpet", () -> new FuelBlockItem(SnufflesBlocks.SNUFFLE_FLUFF_CARPET.get(), 67, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<Item> FROSTY_FLUFF_CARPET = ITEMS.register("frosty_fluff_carpet", () -> new BlockItem(SnufflesBlocks.FROSTY_FLUFF_CARPET.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<Item> SNUFFLE_SPAWN_EGG = ITEMS.register("snuffle_spawn_egg",
            () -> new ForgeSpawnEggItem(SnufflesEntityTypes.SNUFFLE, 16777215, 7125720, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
}