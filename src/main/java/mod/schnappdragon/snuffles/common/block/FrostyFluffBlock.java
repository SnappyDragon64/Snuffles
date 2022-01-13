package mod.schnappdragon.snuffles.common.block;

import mod.schnappdragon.snuffles.core.registry.SnufflesBlocks;
import mod.schnappdragon.snuffles.core.registry.SnufflesParticleTypes;
import mod.schnappdragon.snuffles.core.registry.SnufflesSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class FrostyFluffBlock extends Block {
    public FrostyFluffBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float damage) {
        super.fallOn(world, state, pos, entity, damage * 0.5F);
    }

    @Override
    public void onCaughtFire(BlockState state, Level world, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        world.setBlock(pos, SnufflesBlocks.SNUFFLE_FLUFF.get().defaultBlockState(), 2);
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if ((entity.xOld != entity.getX() || entity.yOld != entity.getY() || entity.zOld != entity.getZ()) && world.getRandom().nextBoolean()) {
            if (world.isClientSide && entity instanceof Player)
                world.addParticle(SnufflesParticleTypes.SNOWFLAKE.get(), entity.getX(), entity.getY(), entity.getZ(), Mth.randomBetween(world.getRandom(), -1.0F, 1.0F) * 0.083F, 0.05F, Mth.randomBetween(world.getRandom(), -1.0F, 1.0F) * 0.083F);
            else if (!world.isClientSide)
                ((ServerLevel) world).sendParticles(SnufflesParticleTypes.SNOWFLAKE.get(), entity.getX(), entity.getY(), entity.getZ(), 0, Mth.randomBetween(world.getRandom(), -1.0F, 1.0F) * 0.083F, 0.05F, Mth.randomBetween(world.getRandom(), -1.0F, 1.0F) * 0.083F, 1.0F);
        }

        super.stepOn(world, pos, state, entity);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(Items.MAGMA_CREAM)) {
            if (!world.isClientSide) {
                if (!player.isCreative())
                    stack.shrink(1);

                world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                world.setBlockAndUpdate(pos, SnufflesBlocks.SNUFFLE_FLUFF.get().defaultBlockState());
                world.playSound(null, pos, SnufflesSoundEvents.FROSTY_FLUFF_THAW.get(), SoundSource.BLOCKS, 0.7F, 1.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.4F);
            }

            return InteractionResult.sidedSuccess(world.isClientSide);
        }

        return super.use(state, world, pos, player, hand, hitResult);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
        if (!world.getBlockState(pos.below()).canOcclude())
            world.addParticle(SnufflesParticleTypes.SNOWFLAKE.get(), pos.getX() + random.nextDouble(), pos.getY() - 0.1F, pos.getZ() + random.nextDouble(), 0.0F, 0.0F, 0.0F);
    }
}