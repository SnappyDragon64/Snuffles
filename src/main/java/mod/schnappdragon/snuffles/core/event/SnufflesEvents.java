package mod.schnappdragon.snuffles.core.event;

import mod.schnappdragon.snuffles.common.block.SnuffleFluffBlock;
import mod.schnappdragon.snuffles.core.Snuffles;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = Snuffles.MODID)
public class SnufflesEvents {
    @SubscribeEvent
    public static void onPlayerBreak(PlayerEvent.BreakSpeed event) {
        if (event.getState().getBlock() instanceof SnuffleFluffBlock && event.getEntity().getMainHandItem().is(Tags.Items.TOOLS_SHEARS))
            event.setNewSpeed(event.getNewSpeed() * 5.0F);
    }
}