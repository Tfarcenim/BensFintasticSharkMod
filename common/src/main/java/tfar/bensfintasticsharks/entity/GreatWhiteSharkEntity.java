package tfar.bensfintasticsharks.entity;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import tfar.bensfintasticsharks.init.ModTags;

import javax.annotation.Nullable;
import java.util.function.IntFunction;

public class GreatWhiteSharkEntity extends WaterAnimal implements ConditionalGlowing {
    protected GreatWhiteSharkEntity(EntityType<? extends WaterAnimal> $$0, Level $$1) {
        super($$0, $$1);

        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 1/8f, 0, false);
        this.lookControl = new DontTurnHeadSwimmingLookControl(this, 10);
    }

    private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(GreatWhiteSharkEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_DEEP_BLUE = SynchedEntityData.defineId(GreatWhiteSharkEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> DATA_GRAB_TIMER = SynchedEntityData.defineId(GreatWhiteSharkEntity.class, EntityDataSerializers.INT);


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 120).add(Attributes.MOVEMENT_SPEED, 1.2F).add(Attributes.ATTACK_DAMAGE, 8);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_VARIANT, 0);
        entityData.define(DATA_DEEP_BLUE,false);
        entityData.define(DATA_GRAB_TIMER,0);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(Items.BLUE_DYE)) {
            if (!this.level().isClientSide) {
                setBlue(true);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.CONSUME;
            }
        } else {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    protected void positionRider(Entity entity, MoveFunction function) {
        Vec3 look = getLookAngle();
        float dist = 3f;


        double angle = computeGrabAngle();

        double rotatedx = look.x * Mth.cos((float) (angle * Math.PI / 180)) - look.z * Mth.sin((float) (angle * Math.PI / 180));
        double rotatedz = look.x * Mth.sin((float) (angle * Math.PI / 180)) + look.z * Mth.cos((float) (angle * Math.PI / 180));

        double offsetX = dist * rotatedx;
        double offsetZ = dist * rotatedz;

        double thisHeight = getDimensions(entity.getPose()).height;
        double height = entity.getDimensions(entity.getPose()).height;

        function.accept(entity, getX() + offsetX, getY() +thisHeight/2- height/2, getZ() + offsetZ);
    }

    float computeGrabAngle() {
        int grabTimer = 100000 - getGrabTimer();

        int mod = grabTimer;//(grabTimer + 3) % 40;

        float degrees = mod * 24;
        float angle =  35 * Mth.sin((float) (degrees * Math.PI /180));
        return angle;

        //.25 - .1 full wave

    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        int grabCountdown = getGrabTimer();
        if (grabCountdown > 0) {
            setGrabTimer(--grabCountdown);
            if (grabCountdown == 0) {
                ejectPassengers();
            }
        }
    }


    void setGrabTimer(int timer) {
        entityData.set(DATA_GRAB_TIMER,timer);
    }

    int getGrabTimer() {
        return entityData.get(DATA_GRAB_TIMER);
    }

    @Override
    public double getMeleeAttackRangeSqr(LivingEntity pEntity) {
        float v = this.getBbWidth() * this.getBbWidth() + pEntity.getBbWidth();
        return v;//cuts default range in half
    }

    @Override
    public double getPerceivedTargetDistanceSquareForMeleeAttack(LivingEntity pEntity) {
        double v = getAttackPosition().distanceToSqr(pEntity.position());
        return v;
        //return Math.max(this.distanceToSqr(pEntity.getMeleeAttackReferencePosition()), this.distanceToSqr(pEntity.position()));
    }


    //this starts at bottom center
    protected Vec3 getAttackPosition() {
        Vec3 bottomCenter = position();
        Vec3 center = bottomCenter.add(0,getBbHeight() / 2,0);
        Vec3 look = getLookAngle();
        float scale = 2;
        return center.add(look.x * scale,0,look.z * scale);
    }

    public boolean canTarget(LivingEntity target) {
        if (target instanceof GreatWhiteSharkEntity) return false;
        if (target instanceof Player player && player.isCreative()) return false;
        if (!isInWater()) return false;
        if (target.isDeadOrDying()) return false;
        if (target.getVehicle() == this) return false;

        if (target.getType().is(ModTags.EntityTypes.GREAT_WHITE_SHARK_ALWAYS_ATTACKS)) return true;

        if (target.getHealth() / target.getMaxHealth() <= .5) return true;

        return false;
    }

    public boolean isBeached() {
        return !isInWaterOrBubble() && onGround();
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
    public void setDeltaMovement(Vec3 $$0) {
        super.setDeltaMovement($$0);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setVariant(Variant.byId(tag.getInt("Variant")));
        setBlue(tag.getBoolean("DeepBlue"));
    }


    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", getVariant().getId());
        tag.putBoolean("DeepBlue",hasBlue());
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        RandomSource randomsource = pLevel.getRandom();
        this.setVariant(Variant.getSpawnVariant(randomsource));
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public boolean isSharkinator() {
        return hasCustomName() && getCustomName().getString().equals("Sharkinator");
    }

    public boolean isDeepBlue() {
        return hasCustomName() && getCustomName().getString().equals("Deep Blue") && hasBlue();
    }

    @Override
    protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
        return $$1.height * 0.65F;
    }


    public Variant getVariant() {
        if (isSharkinator()) {
            return Variant.SPECIMEN_8;
        } else if (isDeepBlue()) {
            return Variant.DEEP_BLUE;
        }

        return Variant.byId(this.entityData.get(DATA_VARIANT));
    }

    public void setVariant(Variant pVariant) {
        this.entityData.set(DATA_VARIANT, pVariant.getId());
    }

    public void setBlue(boolean deepBlue) {
        entityData.set(DATA_DEEP_BLUE,deepBlue);
    }

    public boolean hasBlue() {
        return entityData.get(DATA_DEEP_BLUE);
    }

    @Override
    public boolean hasGlowingLayer() {
        return isSharkinator();
    }

    //. Default Skin 1 (Common Spawn Rate)
    //ii. Default Skin 2 (Common Spawn Rate)
    //iii. Default Skin 3 (Common Spawn Rate)
    //iv. Melanistic (Extremely Rare Spawn Rate)
    //v. Albino (Rarest Spawn Rate)

    public enum Variant implements StringRepresentable {
        DEFAULT_1(0, "default_1"),
        DEFAULT_2(1, "default_2"),
        DEFAULT_3(2, "default_3"),
        MELANISTIC(3, "melanistic"),
        ALBINO(4, "albino"),
        SPECIMEN_8(5,"specimen-8"),
        DEEP_BLUE(6,"deep_blue");


        private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(Variant::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final Codec<Variant> CODEC = StringRepresentable.fromEnum(Variant::values);

        private static final SimpleWeightedRandomList<Variant> NATURAL_VARIANTS = SimpleWeightedRandomList.<Variant>builder()
                .add(DEFAULT_1,1000)
                .add(DEFAULT_2,1000)
                .add(DEFAULT_3,1000)
                .add(MELANISTIC,10)
                .add(ALBINO,5)
                .build();

        private final int id;
        private final String name;

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

        @Override
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
