package mod.schnappdragon.snuffles.common.entity.animal;

import mod.schnappdragon.snuffles.core.registry.SnufflesBlocks;
import mod.schnappdragon.snuffles.core.registry.SnufflesEntityTypes;
import mod.schnappdragon.snuffles.core.registry.SnufflesParticleTypes;
import mod.schnappdragon.snuffles.core.registry.SnufflesSoundEvents;
import mod.schnappdragon.snuffles.core.tags.SnufflesBlockTags;
import mod.schnappdragon.snuffles.core.tags.SnufflesItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.neoforge.common.IShearable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class Snuffle extends Animal implements IShearable {
    private static final EntityDataAccessor<Integer> FROST_COUNTER = SynchedEntityData.defineId(Snuffle.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_HAIRSTYLE_ID = SynchedEntityData.defineId(Snuffle.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_FLUFF = SynchedEntityData.defineId(Snuffle.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_FROSTY = SynchedEntityData.defineId(Snuffle.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_LICKING = SynchedEntityData.defineId(Snuffle.class, EntityDataSerializers.BOOLEAN);

    private int fluffGrowTime = 18000 + this.getRandom().nextInt(6000);

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
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new Snuffle.SnuffleClimbOnTopOfPowderSnowGoal(this, this.level()));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new Snuffle.SnuffleTemptGoal(1.1D, Ingredient.of(SnufflesItemTags.SNUFFLE_FOOD), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new Snuffle.FrostGoal());
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
        this.entityData.define(FROST_COUNTER, 0);
        this.entityData.define(DATA_HAIRSTYLE_ID, 0);
        this.entityData.define(DATA_FLUFF, false);
        this.entityData.define(DATA_FROSTY, false);
        this.entityData.define(IS_LICKING, false);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Hairstyle", this.getHairstyleId());
        compound.putBoolean("HasFluff", this.hasFluff());
        compound.putBoolean("Frosty", this.isFrosty());
        compound.putInt("FluffGrowTime", this.fluffGrowTime);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setHairstyleId(compound.getInt("Hairstyle"));
        this.setFluff(compound.getBoolean("HasFluff"));
        this.setFrosty(compound.getBoolean("Frosty"));
        this.fluffGrowTime = compound.getInt("FluffGrowTime");
    }

    public void setFrostCounter(int counter) {
        this.entityData.set(FROST_COUNTER, counter);
    }

    public int getFrostCounter() {
        return this.entityData.get(FROST_COUNTER);
    }

    public boolean isFrosting() {
        return this.getFrostCounter() > 0;
    }

    public void setHairstyleId(int id) {
        this.entityData.set(DATA_HAIRSTYLE_ID, id);
    }

    public int getHairstyleId() {
        return Mth.clamp(this.entityData.get(DATA_HAIRSTYLE_ID), 0, 3);
    }

    public Snuffle.Hairstyle getHairstyle() {
        return Snuffle.Hairstyle.getHairstyleById(this.getHairstyleId());
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

    public void setLicking(boolean isLicking) {
        this.entityData.set(IS_LICKING, isLicking);
    }

    public boolean isLicking() {
        return this.entityData.get(IS_LICKING);
    }

    /*
     * Frost & Fluff Methods
     */

    @Override
    public void tick() {
        super.tick();

        if (this.isFrosty()) {
            if (this.isOnFire()) {
                this.clearFire();
                this.setFrosty(false);
                this.playEntityOnFireExtinguishedSound();
            }

            if ((this.xOld != this.getX() || this.yOld != this.getY() || this.zOld != this.getZ()) && this.getRandom().nextBoolean())
                this.level().addParticle(SnufflesParticleTypes.SNOWFLAKE.get(), this.getRandomX(0.4D), this.getRandomY(), this.getRandomZ(0.4D), 0.0D, 0.0D, 0.0D);
        }

        if (!this.hasFluff() && !this.isBaby()) {
            if (this.fluffGrowTime > 0)
                --this.fluffGrowTime;
            else
                this.setFluff(true);
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
            return world.getBiome(pos).value().coldEnoughToSnow(pos);
    }

    /*
     * Interaction Methods
     */

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(Items.SLIME_BALL)) {
            if (!this.level().isClientSide) {
                this.usePlayerItem(player, hand, stack);
                this.setHairstyleId((this.getHairstyleId() + 1) % 4);
                this.playSound(SnufflesSoundEvents.SNUFFLE_STYLE.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else if (stack.is(Items.MAGMA_CREAM) && this.isFrosty()) {
            if (!this.level().isClientSide) {
                this.setFrosty(false);
                this.usePlayerItem(player, hand, stack);
                this.playSound(SnufflesSoundEvents.SNUFFLE_THAW.get(), 0.7F, 1.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    protected void usePlayerItem(Player player, InteractionHand hand, ItemStack stack) {
        if (this.isFood(stack))
            this.playSound(SnufflesSoundEvents.SNUFFLE_EAT.get(), 1.0F, 1.0F);

        super.usePlayerItem(player, hand, stack);
    }

    /*
     * Sound Methods
     */

    protected SoundEvent getAmbientSound() {
        return SnufflesSoundEvents.SNUFFLE_AMBIENT.get();
    }

    protected SoundEvent getDeathSound() {
        return SnufflesSoundEvents.SNUFFLE_DEATH.get();
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SnufflesSoundEvents.SNUFFLE_HURT.get();
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SnufflesSoundEvents.SNUFFLE_STEP.get(), 0.15F, 1.0F);
    }

    /*
     * Client Method
     */

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 10) {
            for (int i = 0; i < 4; i ++)
                this.level().addParticle(SnufflesParticleTypes.SNOWFLAKE.get(), this.getRandomX(0.8D), this.getEyeY(), this.getRandomZ(0.8D), 0.0D, 0.1D, 0.0D);
        } else
            super.handleEntityEvent(id);
    }

    /*
     * Shearing Methods
     */

    @Override
    public boolean isShearable(@NotNull ItemStack item, Level world, BlockPos pos) {
        return this.isAlive() && !this.isBaby() && this.hasFluff();
    }

    @NotNull
    @Override
    public List<ItemStack> onSheared(@Nullable Player player, @NotNull ItemStack item, Level world, BlockPos pos, int fortune) {
        return onSheared(player, item, world, pos, fortune, SoundSource.PLAYERS);
    }

    @NotNull
    public List<ItemStack> onSheared(@Nullable Player player, @NotNull ItemStack item, Level world, BlockPos pos, int fortune, SoundSource source) {
        this.setFluff(false);
        this.level().gameEvent(player, GameEvent.SHEAR, pos);
        this.fluffGrowTime = 18000 + this.getRandom().nextInt(6000);
        this.level().playSound(null, this, SnufflesSoundEvents.SNUFFLE_SHEAR.get(), source, 1.0F, 1.0F);
        return List.of(new ItemStack(this.isFrosty() ? SnufflesBlocks.FROSTY_FLUFF.get() : SnufflesBlocks.SNUFFLE_FLUFF.get()));
    }

    /*
     * Spawning Methods
     */

    public static boolean checkSnuffleSpawnRules(EntityType<Snuffle> snuffle, LevelAccessor world, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return world.getBlockState(pos.below()).is(SnufflesBlockTags.SNUFFLES_SPAWNABLE_ON) && isBrightEnoughToSpawn(world, pos);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag compound) {
        boolean frosty = world.getBiome(this.blockPosition()).value().coldEnoughToSnow(this.blockPosition());

        if (groupData instanceof Snuffle.SnuffleGroupData)
            frosty = ((Snuffle.SnuffleGroupData) groupData).frosty;
        else
            groupData = new Snuffle.SnuffleGroupData(frosty);

        Snuffle.SnuffleGroupData snuffleGroupData = (Snuffle.SnuffleGroupData) groupData;
        if (snuffleGroupData.getGroupSize() > 0 && this.random.nextFloat() <= snuffleGroupData.getBabySpawnChance())
            this.setAge(-24000);
        else
            this.setFluff(true);

        this.setFrosty(frosty);
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
     * Death Method
     */

    @Override
    public void die(DamageSource source) {
        this.setFrostCounter(0);
        super.die(source);
    }

    /*
     * Data
     */

    public static class SnuffleGroupData extends AgeableMob.AgeableMobGroupData {
        public final boolean frosty;

        public SnuffleGroupData(boolean frosty) {
            super(false);
            this.frosty = frosty;
        }
    }

    /*
     * Hairstyle Enum
     */

    public enum Hairstyle {
        DEFAULT,
        SHEEPDOG,
        PORO,
        HORSESHOE;

        private static final Hairstyle[] HAIRSTYLES = Hairstyle.values();

        public static Hairstyle getHairstyleById(int id) {
            return HAIRSTYLES[id];
        }
    }

    /*
     * AI Goals
     */

    class SnuffleClimbOnTopOfPowderSnowGoal extends net.minecraft.world.entity.ai.goal.ClimbOnTopOfPowderSnowGoal {
        public SnuffleClimbOnTopOfPowderSnowGoal(Mob mob, Level world) {
            super(mob, world);
        }

        @Override
        public boolean canUse() {
            boolean flag = Snuffle.this.wasInPowderSnow || Snuffle.this.isInPowderSnow;
            if (flag && Snuffle.this.getType().is(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS)) {
                BlockPos blockpos = Snuffle.this.blockPosition().above();
                BlockState blockstate = Snuffle.this.level().getBlockState(blockpos);
                return blockstate.is(Blocks.POWDER_SNOW) || blockstate.getCollisionShape(Snuffle.this.level(), blockpos) == Shapes.empty() && Snuffle.this.isFrosty();
            } else {
                return false;
            }
        }
    }

    class SnuffleTemptGoal extends TemptGoal {
        public SnuffleTemptGoal(double speedModifier, Ingredient items, boolean canScare) {
            super(Snuffle.this, speedModifier, items, canScare);
        }

        public void start() {
            super.start();
            Snuffle.this.setLicking(true);
        }

        public void stop() {
            super.stop();
            Snuffle.this.setLicking(false);
        }
    }

    class FrostGoal extends Goal {
        private static final int WAIT_TIME_BEFORE_FROST = reducedTickDelay(140);
        private int countdown = Snuffle.this.random.nextInt(WAIT_TIME_BEFORE_FROST);

        public FrostGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
        }

        public boolean canUse() {
            return Snuffle.this.xxa == 0.0F && Snuffle.this.yya == 0.0F && Snuffle.this.zza == 0.0F && this.canFrost();
        }

        public boolean canContinueToUse() {
            return Snuffle.this.isFrosting() && this.canFrost();
        }

        private boolean canFrost() {
            if (this.countdown > 0) {
                --this.countdown;
                return false;
            } else {
                Level world = Snuffle.this.level();
                BlockPos pos = Snuffle.this.blockPosition();
                return !Snuffle.this.isFrosty() && (Snuffle.this.isSnowingAt(world, pos) || world.getBlockState(pos).is(Blocks.POWDER_SNOW));
            }
        }

        public void tick() {
            Snuffle.this.setFrostCounter(Math.max(0, Snuffle.this.getFrostCounter() - 1));

            if (Snuffle.this.getFrostCounter() % this.adjustedTickDelay(4) == 0) {
                Snuffle.this.playSound(SnufflesSoundEvents.SNUFFLE_SHAKE.get(), 1.0F, (Snuffle.this.random.nextFloat() - Snuffle.this.random.nextFloat()) * 0.2F + 1.0F);
                Snuffle.this.setFrosty(Snuffle.this.getFrostCounter() == this.adjustedTickDelay(4));
                Snuffle.this.level().broadcastEntityEvent(Snuffle.this, (byte) 10);
            }
        }

        public void start() {
            Snuffle.this.setFrostCounter(this.adjustedTickDelay(40));
            Snuffle.this.gameEvent(GameEvent.ENTITY_ACTION);
            Snuffle.this.getNavigation().stop();
        }

        public void stop() {
            Snuffle.this.setFrostCounter(0);
            this.countdown = Snuffle.this.random.nextInt(WAIT_TIME_BEFORE_FROST);
        }
    }
}