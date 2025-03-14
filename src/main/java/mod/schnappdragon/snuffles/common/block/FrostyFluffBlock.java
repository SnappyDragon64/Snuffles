package mod.schnappdragon.snuffles.common.block;

import mod.schnappdragon.snuffles.core.registry.SnufflesBlocks;
import mod.schnappdragon.snuffles.core.registry.SnufflesParticleTypes;
import mod.schnappdragon.snuffles.core.registry.SnufflesSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FrostyFluffBlock extends Block {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);

    public FrostyFluffBlock(Properties properties) {
        super(properties);
    }

    public @NotNull VoxelShape getCollisionShape(BlockState p_56702_, BlockGetter p_56703_, BlockPos p_56704_, CollisionContext p_56705_) {
        return SHAPE;
    }

    public @NotNull VoxelShape getBlockSupportShape(BlockState p_56707_, BlockGetter p_56708_, BlockPos p_56709_) {
        return Shapes.block();
    }

    public @NotNull VoxelShape getVisualShape(BlockState p_56684_, BlockGetter p_56685_, BlockPos p_56686_, CollisionContext p_56687_) {
        return Shapes.block();
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
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && !entity.isSteppingCarefully() && !world.isClientSide) {
            if (entity.xOld != entity.getX() || entity.yOld != entity.getY() || entity.zOld != entity.getZ()) {
                if (world.getRandom().nextInt(16) == 0) {
                    ((ServerLevel) world).sendParticles(SnufflesParticleTypes.SNOWFLAKE.get(), entity.getX(), entity.getY(), entity.getZ(), 0, Mth.randomBetween(world.getRandom(), -1.0F, 1.0F) * 0.083F, 0.05F, Mth.randomBetween(world.getRandom(), -1.0F, 1.0F) * 0.083F, 1.0F);
                }
            }
        }

        super.entityInside(state, world, pos, entity);
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.is(Items.MAGMA_CREAM)) {
            if (!world.isClientSide) {
                if (!player.isCreative())
                    stack.shrink(1);

                world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                world.setBlockAndUpdate(pos, SnufflesBlocks.SNUFFLE_FLUFF.get().defaultBlockState());
                world.playSound(null, pos, SnufflesSoundEvents.FROSTY_FLUFF_THAW.get(), SoundSource.BLOCKS, 0.7F, 1.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.4F);
            }

            return ItemInteractionResult.sidedSuccess(world.isClientSide);
        }

        return super.useItemOn(stack, state, world, pos, player, hand, hitResult);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (!world.getBlockState(pos.below()).canOcclude())
            world.addParticle(SnufflesParticleTypes.SNOWFLAKE.get(), pos.getX() + random.nextDouble(), pos.getY() - 0.1F, pos.getZ() + random.nextDouble(), 0.0F, 0.0F, 0.0F);
    }
}