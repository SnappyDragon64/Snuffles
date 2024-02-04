package mod.schnappdragon.snuffles.core;

import mod.schnappdragon.snuffles.core.dispenser.SnufflesShearsDispenseItemBehavior;
import mod.schnappdragon.snuffles.core.misc.SnufflesFlammables;
import mod.schnappdragon.snuffles.core.misc.SnufflesSpawns;
import mod.schnappdragon.snuffles.core.registry.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Snuffles.MODID)
public class Snuffles {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "snuffles";

    public Snuffles(IEventBus bus) {
        bus.addListener(this::commonSetup);

        SnufflesBlocks.BLOCKS.register(bus);
        SnufflesItems.ITEMS.register(bus);
        SnufflesEntityTypes.ENTITY_TYPES.register(bus);
        SnufflesParticleTypes.PARTICLE_TYPES.register(bus);
        SnufflesSoundEvents.SOUND_EVENTS.register(bus);
    }

    @SubscribeEvent
    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            SnufflesSpawns.registerSpawns();
            SnufflesFlammables.registerFlammables();
            SnufflesShearsDispenseItemBehavior.registerDispenserBehaviors();
        });
    }
}