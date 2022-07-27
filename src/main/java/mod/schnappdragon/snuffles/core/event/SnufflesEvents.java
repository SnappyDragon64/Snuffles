package mod.schnappdragon.snuffles.core.event;

import mod.schnappdragon.snuffles.common.block.SnuffleFluffBlock;
import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Snuffles.MODID)
public class SnufflesEvents {
    @SubscribeEvent
    public static void onPlayerBreak(PlayerEvent.BreakSpeed event) {
        if (event.getState().getBlock() instanceof SnuffleFluffBlock && event.getEntity().getMainHandItem().is(Tags.Items.SHEARS))
            event.setNewSpeed(event.getNewSpeed() * 5.0F);
    }
}