package mod.schnappdragon.snuffles.common.block;

import mod.schnappdragon.snuffles.core.registry.SnufflesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

public class SnuffleFluffBlock extends Block {
    public SnuffleFluffBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float damage) {
        super.fallOn(world, state, pos, entity, damage * 0.5F);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (this.isSnowingAt(world, pos.above()))
            world.setBlock(pos, SnufflesBlocks.FROSTY_FLUFF.get().defaultBlockState(), 2);
    }

    private boolean isSnowingAt(ServerLevel world, BlockPos pos) {
        if (!world.isRaining())
            return false;
        else if (!world.canSeeSky(pos))
            return false;
        else if (world.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos).getY() > pos.getY())
            return false;
        else
            return world.getBiome(pos).value().coldEnoughToSnow(pos);
    }
}