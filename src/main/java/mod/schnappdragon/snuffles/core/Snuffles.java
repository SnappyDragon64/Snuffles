package mod.schnappdragon.snuffles.core;

import mod.schnappdragon.snuffles.client.renderer.SnufflesRenderLayers;
import mod.schnappdragon.snuffles.core.misc.SnufflesFlammables;
import mod.schnappdragon.snuffles.core.registry.SnuffleItems;
import mod.schnappdragon.snuffles.core.registry.SnufflesBlocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Snuffles.MODID)
public class Snuffles {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "snuffles";

    public Snuffles() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        SnufflesBlocks.BLOCKS.register(modEventBus);
        SnuffleItems.ITEMS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            SnufflesFlammables.registerFlammables();
        });
    }

    private void clientSetup(FMLClientSetupEvent event) {
        SnufflesRenderLayers.registerRenderLayers();
    }
}