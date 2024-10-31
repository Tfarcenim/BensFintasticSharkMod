package tfar.bensfintasticsharks.entity;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import tfar.bensfintasticsharks.init.ModTags;

import javax.annotation.Nullable;
import java.util.function.IntFunction;

public class CommonThresherSharkEntity extends WaterAnimal implements ConditionalGlowing {
    protected CommonThresherSharkEntity(EntityType<? extends WaterAnimal> $$0, Level $$1) {
        super($$0, $$1);

        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 1/8f, 0, false);
        this.lookControl = new DontTurnHeadSwimmingLookControl(this, 10);
    }

    private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(CommonThresherSharkEntity.class, EntityDataSerializers.INT);

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 110).add(Attributes.MOVEMENT_SPEED, 1.2F).add(Attributes.ATTACK_DAMAGE, 6);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VARIANT, 0);
    }

    public boolean canTarget(LivingEntity target) {
        if (target instanceof CommonThresherSharkEntity) return false;
        if (target instanceof Player player && player.isCreative()) return false;
        if (!isInWater()) return false;
        if (target.isDeadOrDying()) return false;

        if (target.getType().is(ModTags.EntityTypes.COMMON_THRESHER_SHARK_ALWAYS_ATTACKS)) return true;

        if (target.getHealth() / target.getMaxHealth() <= .5) return true;

        return false;
    }

    @Override
    public double getMeleeAttackRangeSqr(LivingEntity pEntity) {
        float v = this.getBbWidth() * this.getBbWidth() + pEntity.getBbWidth();
        return v;//cuts default range in half
    }

    @Override
    public double getPerceivedTargetDistanceSquareForMeleeAttack(LivingEntity pEntity) {
        double v = getBiteAttackPosition().distanceToSqr(pEntity.position());
        return v;
        //return Math.max(this.distanceToSqr(pEntity.getMeleeAttackReferencePosition()), this.distanceToSqr(pEntity.position()));
    }


    //this starts at bottom center
    protected Vec3 getBiteAttackPosition() {
        Vec3 bottomCenter = position();
        Vec3 center = bottomCenter.add(0,getBbHeight() / 2,0);
        Vec3 look = getLookAngle();
        float scale = 2;
        return center.add(look.x * scale,0,look.z * scale);
    }

    @Override
    public void travel(Vec3 movementInput) {
        if (this.tickCount % 10 == 0)
            this.refreshDimensions();

        if (isEffectiveAi() && this.isInWater()) {
            moveRelative(getSpeed(), movementInput);
            move(MoverType.SELF, getDeltaMovement());
            setDeltaMovement(getDeltaMovement().scale(this.wasTouchingWater ? 0.65 : 0.25));
            if (getTarget() == null)
                setDeltaMovement(getDeltaMovement().add(0.0, -0.005, 0.0));
        } else
            super.travel(movementInput);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        RandomSource randomsource = pLevel.getRandom();
        this.setVariant(Variant.getSpawnVariant(randomsource));
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public void thunderHit(ServerLevel $$0, LightningBolt $$1) {
        setVariant(Variant.ZIPPY);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setVariant(Variant.byId(tag.getInt("Variant")));
    }


    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", getVariant().getId());
    }


    public Variant getVariant() {
        return Variant.byId(this.entityData.get(DATA_VARIANT));
    }

    public void setVariant(Variant pVariant) {
        this.entityData.set(DATA_VARIANT, pVariant.getId());
    }

    public boolean isZippy() {
        return getVariant() == Variant.ZIPPY;
    }

    @Override
    public boolean hasGlowingLayer() {
        return isZippy();
    }

    //i. Default Skin 1 (Common Spawn Rate)
    //ii. Melanistic (Extremely Rare Spawn Rate)
    //iii. Albino Variant (Rarest Spawn Rate)
    //4
    //iv. Zippy (Easter Egg) Requires Shark Trident and Thunderstorm
    //{Glows in the dark}

    public enum Variant implements StringRepresentable {
        DEFAULT_1(0, "default_1"),
        MELANISTIC(1, "melanistic"),
        ALBINO(2, "albino"),
        ZIPPY(3,"zippy");

        private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(Variant::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final Codec<Variant> CODEC = StringRepresentable.fromEnum(Variant::values);
        private final int id;
        private final String name;

        private static final SimpleWeightedRandomList<Variant> NATURAL_VARIANTS = SimpleWeightedRandomList.<Variant>builder()
                .add(DEFAULT_1,1000)
                .add(MELANISTIC,10)
                .add(ALBINO,5)
                .build();

        Variant(int pId, String pName) {
            this.id = pId;
            this.name = pName;
        }

        public int getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public String getSerializedName() {
            return this.name;
        }

        public static Variant byId(int pId) {
            return BY_ID.apply(pId);
        }


        private static Variant getSpawnVariant(RandomSource pRandom) {
            return NATURAL_VARIANTS.getRandomValue(pRandom).orElseThrow();
        }
    }
}
