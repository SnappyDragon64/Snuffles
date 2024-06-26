package mod.schnappdragon.snuffles.core.registry;

import mod.schnappdragon.snuffles.common.item.FuelBlockItem;
import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;

@EventBusSubscriber(modid = Snuffles.MODID, bus = EventBusSubscriber.Bus.MOD)
public class SnufflesItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Snuffles.MODID);

    public static final DeferredItem<Item> SNUFFLE_FLUFF = ITEMS.register("snuffle_fluff", () -> new FuelBlockItem(SnufflesBlocks.SNUFFLE_FLUFF.get(), 100, new Item.Properties()));
    public static final DeferredItem<Item> FROSTY_FLUFF = ITEMS.register("frosty_fluff", () -> new BlockItem(SnufflesBlocks.FROSTY_FLUFF.get(), new Item.Properties()));
    public static final DeferredItem<Item> SNUFFLE_FLUFF_CARPET = ITEMS.register("snuffle_fluff_carpet", () -> new FuelBlockItem(SnufflesBlocks.SNUFFLE_FLUFF_CARPET.get(), 67, new Item.Properties()));
    public static final DeferredItem<Item> FROSTY_FLUFF_CARPET = ITEMS.register("frosty_fluff_carpet", () -> new BlockItem(SnufflesBlocks.FROSTY_FLUFF_CARPET.get(), new Item.Properties()));

    public static final DeferredItem<Item> SNUFFLE_SPAWN_EGG = ITEMS.register("snuffle_spawn_egg",
            () -> new DeferredSpawnEggItem(SnufflesEntityTypes.SNUFFLE, 16777215, 7125720, new Item.Properties()));

    @SubscribeEvent
    public static void registerCreativeTabsItem(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(SNUFFLE_SPAWN_EGG);
        } else if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(SNUFFLE_FLUFF);
            event.accept(FROSTY_FLUFF);
            event.accept(SNUFFLE_FLUFF_CARPET);
            event.accept(FROSTY_FLUFF_CARPET);
        }
    }
}
