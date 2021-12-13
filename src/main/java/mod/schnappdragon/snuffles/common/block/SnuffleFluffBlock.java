package mod.schnappdragon.snuffles.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SnuffleFluffBlock extends Block {
    public SnuffleFluffBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float damage) {
        super.fallOn(world, state, pos, entity, damage * 0.5F);
    }
}
