package mod.schnappdragon.snuffles.core;

import mod.schnappdragon.snuffles.core.dispenser.SnufflesShearsDispenseItemBehavior;
import mod.schnappdragon.snuffles.core.misc.SnufflesFlammables;
import mod.schnappdragon.snuffles.core.misc.SnufflesSpawns;
import mod.schnappdragon.snuffles.core.registry.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Snuffles.MODID)
public class Snuffles {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "snuffles";

    public Snuffles() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        SnufflesBlocks.BLOCKS.register(modEventBus);
        SnufflesItems.ITEMS.register(modEventBus);
        SnufflesEntityTypes.ENTITY_TYPES.register(modEventBus);
        SnufflesParticleTypes.PARTICLE_TYPES.register(modEventBus);
        SnufflesSoundEvents.SOUND_EVENTS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            SnufflesSpawns.registerSpawns();
            SnufflesFlammables.registerFlammables();
            SnufflesShearsDispenseItemBehavior.registerDispenserBehaviors();
        });
    }
}