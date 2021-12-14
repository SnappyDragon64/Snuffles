package mod.schnappdragon.snuffles.common.block;

import mod.schnappdragon.snuffles.core.registry.SnufflesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class FrostyFluffBlock extends SnuffleFluffBlock {
    public FrostyFluffBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onCaughtFire(BlockState state, Level world, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        world.setBlock(pos, SnufflesBlocks.SNUFFLE_FLUFF.get().defaultBlockState(), 2);
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if ((entity.xOld != entity.getX() || entity.yOld != entity.getY() || entity.zOld != entity.getZ()) && world.getRandom().nextBoolean()) {
            if (world.isClientSide && entity instanceof Player)
                world.addParticle(ParticleTypes.SNOWFLAKE, entity.getX(), entity.getY(), entity.getZ(), Mth.randomBetween(world.getRandom(), -1.0F, 1.0F) * 0.083F, 0.05F, Mth.randomBetween(world.getRandom(), -1.0F, 1.0F) * 0.083F);
            else if (!world.isClientSide)
                ((ServerLevel) world).sendParticles(ParticleTypes.SNOWFLAKE, entity.getX(), entity.getY(), entity.getZ(), 0, Mth.randomBetween(world.getRandom(), -1.0F, 1.0F) * 0.083F, 0.05F, Mth.randomBetween(world.getRandom(), -1.0F, 1.0F) * 0.083F, 1.0F);
        }

        super.stepOn(world, pos, state, entity);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
        if (!world.getBlockState(pos.below()).canOcclude())
            world.addParticle(ParticleTypes.SNOWFLAKE, pos.getX() + random.nextDouble(), pos.getY() - 0.1F, pos.getZ() + random.nextDouble(), 0.0F, 0.0F, 0.0F);
    }
}