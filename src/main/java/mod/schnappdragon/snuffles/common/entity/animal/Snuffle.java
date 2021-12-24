package mod.schnappdragon.snuffles.common.entity.animal;

import mod.schnappdragon.snuffles.core.registry.SnufflesEntityTypes;
import mod.schnappdragon.snuffles.core.registry.SnufflesParticleTypes;
import mod.schnappdragon.snuffles.core.tags.SnufflesBlockTags;
import mod.schnappdragon.snuffles.core.tags.SnufflesItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class Snuffle extends Animal {
    private static final EntityDataAccessor<Integer> DATA_HAIRSTYLE_ID = SynchedEntityData.defineId(Snuffle.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_FLUFF = SynchedEntityData.defineId(Snuffle.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_FROSTY = SynchedEntityData.defineId(Snuffle.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_CRAVING = SynchedEntityData.defineId(Snuffle.class, EntityDataSerializers.BOOLEAN);

    private int frostTicks;

    public Snuffle(EntityType<Snuffle> snuffle, Level world) {
        super(snuffle, world);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2F)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new Snuffle.SnuffleTemptGoal(1.1D, Ingredient.of(SnufflesItemTags.SNUFFLE_FOOD), false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    public Vec3 getLeashOffset() {
        return new Vec3(0.0D, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }

    /*
     * Data Methods
     */

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_HAIRSTYLE_ID, 0);
        this.entityData.define(DATA_FLUFF, false);
        this.entityData.define(DATA_FROSTY, false);
        this.entityData.define(IS_CRAVING, false);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Hairstyle", this.getHairstyle());
        compound.putBoolean("Fluff", this.hasFluff());
        compound.putBoolean("Frosty", this.isFrosty());
        compound.putInt("FrostTicks", this.frostTicks);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setHairstyle(compound.getInt("Hairstyle"));
        this.setFluff(compound.getBoolean("Fluff"));
        this.setFrosty(compound.getBoolean("Frosty"));
        this.frostTicks = compound.getInt("FrostTicks");
    }

    public void setHairstyle(int id) {
        this.entityData.set(DATA_HAIRSTYLE_ID, id);
    }

    public int getHairstyle() {
        return Mth.clamp(this.entityData.get(DATA_HAIRSTYLE_ID), 0, 3);
    }

    public void setFluff(boolean hasFluff) {
        this.entityData.set(DATA_FLUFF, hasFluff);
    }

    public boolean hasFluff() {
        return this.entityData.get(DATA_FLUFF);
    }

    public void setFrosty(boolean isFrosty) {
        this.entityData.set(DATA_FROSTY, isFrosty);
    }

    public boolean isFrosty() {
        return this.entityData.get(DATA_FROSTY);
    }

    public void setCraving(boolean isCraving) {
        this.entityData.set(IS_CRAVING, isCraving);
    }

    public boolean isCraving() {
        return this.entityData.get(IS_CRAVING);
    }

    /*
     * Frost Methods
     */

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();

        if (!this.isFrosty()) {
            if (this.frostTicks > 0)
                --this.frostTicks;
            else {
                if (this.isSnowingAt(this.level, this.blockPosition()))
                    this.setFrosty(true);

                this.frostTicks = this.getRandom().nextInt(140);
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level.isClientSide) {
            if (this.isInPowderSnow)
                this.setFrosty(true);

            if (this.isOnFire() && this.isFrosty()) {
                this.clearFire();
                this.setFrosty(false);
            }
        } else {
            if (this.isFrosty() && this.getDeltaMovement().lengthSqr() > 0.0081D && this.getRandom().nextBoolean())
                this.level.addParticle(SnufflesParticleTypes.SNOWFLAKE.get(), this.getRandomX(0.4D), this.getRandomY(), this.getRandomZ(0.4D), 0.0D, 0.0D, 0.0D);
        }
    }

    private boolean isSnowingAt(Level world, BlockPos pos) {
        if (!world.isRaining())
            return false;
        else if (!world.canSeeSky(pos))
            return false;
        else if (world.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos).getY() > pos.getY())
            return false;
        else
            return world.getBiome(pos).coldEnoughToSnow(pos);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(Items.SLIME_BALL)) {
            if (!this.level.isClientSide) {
                this.usePlayerItem(player, hand, stack);
                this.setHairstyle((this.getHairstyle() + 1) % 4);
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else if (stack.is(Items.MAGMA_CREAM) && this.isFrosty()) {
            if (!this.level.isClientSide) {
                this.usePlayerItem(player, hand, stack);
                this.setFrosty(false);
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    /*
     * Spawning Methods
     */

    public static boolean checkSnuffleSpawnRules(EntityType<Snuffle> snuffle, LevelAccessor world, MobSpawnType spawnType, BlockPos pos, Random random) {
        return world.getBlockState(pos.below()).is(SnufflesBlockTags.SNUFFLES_SPAWNABLE_ON) && isBrightEnoughToSpawn(world, pos);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag compound) {
        this.setFrosty(world.getBiome(this.blockPosition()).coldEnoughToSnow(this.blockPosition()));
        return super.finalizeSpawn(world, difficulty, spawnType, groupData, compound);
    }

    /*
     * Breeding Methods
     */

    @Nullable
    @Override
    public Snuffle getBreedOffspring(ServerLevel world, AgeableMob snuffle) {
        return SnufflesEntityTypes.SNUFFLE.get().create(world);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(SnufflesItemTags.SNUFFLE_FOOD);
    }

    /*
     * AI Goals
     */

    class SnuffleTemptGoal extends TemptGoal {
        public SnuffleTemptGoal(double speedModifier, Ingredient items, boolean canScare) {
            super(Snuffle.this, speedModifier, items, canScare);
        }

        public void start() {
            super.start();
            Snuffle.this.setCraving(true);
        }

        public void stop() {
            super.stop();
            Snuffle.this.setCraving(false);
        }
    }
}