package mod.schnappdragon.snuffles.core.dispenser;

import mod.schnappdragon.snuffles.common.entity.animal.Snuffle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;

public class SnufflesShearsDispenseItemBehavior {
    private static DispenseItemBehavior ShearsBehavior;

    public static void registerDispenserBehaviors() {
        ShearsBehavior = DispenserBlock.DISPENSER_REGISTRY.get(Items.SHEARS);

        DispenserBlock.registerBehavior(Items.SHEARS, new OptionalDispenseItemBehavior() {
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                ServerLevel worldIn = source.level();
                BlockPos pos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));

                for (Snuffle snuffle : worldIn.getEntitiesOfClass(Snuffle.class, new AABB(pos), EntitySelector.NO_SPECTATORS)) {
                    if (snuffle.isShearable(ItemStack.EMPTY, worldIn, pos)) {
                        snuffle.onSheared(null, stack, worldIn, pos, 0, SoundSource.BLOCKS).forEach(drop -> {
                            worldIn.addFreshEntity(new ItemEntity(worldIn, snuffle.getX(), snuffle.getY(1.0D), snuffle.getZ(), drop));
                        });

                        if (stack.hurtAndConvertOnBreak(1, worldIn.getRandom(), null))
                            stack.setCount(0);
                        this.setSuccess(true);
                        return stack;
                    }
                }

                return ShearsBehavior.dispense(source, stack);
            }
        });
    }
}