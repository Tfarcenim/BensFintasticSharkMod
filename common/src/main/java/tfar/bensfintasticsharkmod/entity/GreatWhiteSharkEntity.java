package tfar.bensfintasticsharkmod.entity;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ByIdMap;
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
import tfar.bensfintasticsharkmod.init.ModTags;

import javax.annotation.Nullable;
import java.util.function.IntFunction;

public class GreatWhiteSharkEntity extends WaterAnimal implements ConditionalGlowing {
    protected GreatWhiteSharkEntity(EntityType<? extends WaterAnimal> $$0, Level $$1) {
        super($$0, $$1);

        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 1/10f, .5f/10f, false);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(GreatWhiteSharkEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_DEEP_BLUE = SynchedEntityData.defineId(GreatWhiteSharkEntity.class, EntityDataSerializers.BOOLEAN);

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 1.2F).add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_VARIANT, 0);
        entityData.define(DATA_DEEP_BLUE,false);
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

    public boolean canTarget(LivingEntity target) {
        if (target instanceof GreatWhiteSharkEntity) return false;
        if (target instanceof Player player && player.isCreative()) return false;
        if (!isInWater()) return false;
        if (target.isDeadOrDying()) return false;
        if (target.getVehicle() == this) return false;

        if (getType().is(ModTags.EntityTypes.GREAT_WHITE_SHARK_ALWAYS_ATTACKS)) return true;

        if (target.getHealth() / target.getMaxHealth() <= .5) return true;

        return false;
    }


    @Override
    protected void positionRider(Entity entity, MoveFunction function) {
        double offsetX = 4 *Math.sin(getXRot() * Math.PI / 180);
        double offsetY = 4 * Math.cos(getYRot() * Math.PI / 180);

        function.accept(entity, getX() + offsetX, getY() - 0.15f, getZ() + offsetY);
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
