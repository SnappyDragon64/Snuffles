package mod.schnappdragon.snuffles.client.renderer;

import mod.schnappdragon.snuffles.core.registry.SnufflesBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

public class SnufflesRenderLayers {
    public static void registerRenderLayers() {
        setRenderLayer(SnufflesBlocks.SNUFFLE_FLUFF_CARPET.get(), RenderType.cutout());
        setRenderLayer(SnufflesBlocks.FROSTY_FLUFF_CARPET.get(), RenderType.cutout());
    }

    private static void setRenderLayer(Block block, RenderType type) {
        ItemBlockRenderTypes.setRenderLayer(block, type);
    }
}