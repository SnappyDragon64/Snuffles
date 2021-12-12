package mod.schnappdragon.snuffles.common.block;

import mod.schnappdragon.snuffles.core.registry.SnufflesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class FrostyFluffBlock extends Block {
    public FrostyFluffBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onCaughtFire(BlockState state, Level world, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        world.setBlock(pos, SnufflesBlocks.SNUFFLE_FLUFF.get().defaultBlockState(), 2);
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (world.isClientSide) {
            if ((entity.xOld != entity.getX() || entity.yOld != entity.getY() || entity.zOld != entity.getZ()) && world.getRandom().nextBoolean())
                world.addParticle(ParticleTypes.SNOWFLAKE, entity.getX(), entity.getY(), entity.getZ(), Mth.randomBetween(world.getRandom(), -1.0F, 1.0F) * 0.083F, 0.05F, Mth.randomBetween(world.getRandom(), -1.0F, 1.0F) * 0.083F);
        }

        super.stepOn(world, pos, state, entity);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
        if (random.nextBoolean())
            world.addParticle(ParticleTypes.SNOWFLAKE, pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(), Mth.randomBetween(world.getRandom(), -0.1F, 0.1F) * 0.083F, 0.0F, Mth.randomBetween(world.getRandom(), -0.1F, 0.1F) * 0.083F);
    }
}