package mod.schnappdragon.snuffles.core.registry;

import mod.schnappdragon.snuffles.common.block.FrostyFluffBlock;
import mod.schnappdragon.snuffles.common.block.FrostyFluffCarpetBlock;
import mod.schnappdragon.snuffles.common.block.SnuffleFluffBlock;
import mod.schnappdragon.snuffles.common.block.SnuffleFluffCarpetBlock;
import mod.schnappdragon.snuffles.core.Snuffles;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

public class SnufflesBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Snuffles.MODID);

    public static final DeferredBlock<Block> SNUFFLE_FLUFF = BLOCKS.register("snuffle_fluff", () -> new SnuffleFluffBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().randomTicks().strength(0.6F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> FROSTY_FLUFF = BLOCKS.register("frosty_fluff", () -> new FrostyFluffBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().strength(0.6F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> SNUFFLE_FLUFF_CARPET = BLOCKS.register("snuffle_fluff_carpet", () -> new SnuffleFluffCarpetBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().randomTicks().noCollission().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> FROSTY_FLUFF_CARPET = BLOCKS.register("frosty_fluff_carpet", () -> new FrostyFluffCarpetBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().noCollission().strength(0.1F).sound(SoundType.WOOL)));
}