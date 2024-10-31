package tfar.bensfintasticsharks.entity;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.PathFinder;

import javax.annotation.Nullable;
import java.util.function.IntFunction;
import java.util.function.Predicate;

public class CommonStingrayEntity extends WaterAnimal {


    private static final Predicate<LivingEntity> DANGEROUS = living -> {
        if (living instanceof Player player && player.isCreative()) {
            return false;
        } else {
            return !(living instanceof CommonStingrayEntity);
        }
    };

    static final TargetingConditions targetingConditions = TargetingConditions.forNonCombat().ignoreInvisibilityTesting().ignoreLineOfSight().selector(DANGEROUS);


    protected boolean stinging;

    protected CommonStingrayEntity(EntityType<? extends WaterAnimal> $$0, Level $$1) {
        super($$0, $$1);

        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 1 / 8f, 0, false);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(CommonStingrayEntity.class, EntityDataSerializers.INT);

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10).add(Attributes.MOVEMENT_SPEED, 1.2F).add(Attributes.ATTACK_DAMAGE, 2);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VARIANT, 0);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        RandomSource randomsource = pLevel.getRandom();
        this.setVariant(Variant.getSpawnVariant(randomsource));
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
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

    //. Default Skin 1 (Common Spawn Rate)
    //ii. Default Skin 2 (Common Spawn Rate)
    //iii. Default Skin 3 (Common Spawn Rate)
    //iv. Melanistic (Extremely Rare Spawn Rate)
    //v. Albino (Rarest Spawn Rate)

    public enum Variant implements StringRepresentable {
        DEFAULT_1(0, "default_1"),
        DEFAULT_2(1, "default_2"),
        DEFAULT_3(2, "default_3"),
        DEFAULT_4(3, "default_4");
        private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(Variant::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final Codec<Variant> CODEC = StringRepresentable.fromEnum(Variant::values);

        private static final SimpleWeightedRandomList<Variant> NATURAL_VARIANTS = SimpleWeightedRandomList.<Variant>builder()
                .add(DEFAULT_1, 1000)
                .add(DEFAULT_2, 1000)
                .add(DEFAULT_3, 1000)
                .add(DEFAULT_4, 1000)
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

    public void setStinging(boolean stinging) {
        this.stinging = stinging;
    }

    public boolean isStinging() {
        return stinging;
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        return new WaterBoundPathNavigation(this, pLevel) {
            @Override
            protected boolean canUpdatePath() {
                return true;
            }

            @Override
            protected PathFinder createPathFinder(int pMaxVisitedNodes) {
                nodeEvaluator = new AmphibiousNodeEvaluator(true);
                nodeEvaluator.setCanOpenDoors(false);
                return new PathFinder(this.nodeEvaluator, pMaxVisitedNodes);
            }
        };
    }

    /**
     * Called every tick so the entity can update its state as required. For example, zombies and skeletons use this to
     * react to sunlight and start to burn.
     */
    public void aiStep() {
        super.aiStep();
        stinging = getLastAttacker() != null;
        if (this.isAlive() && isStinging()) {
            this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(0.3D), mob -> targetingConditions.test(this, mob)).stream().filter(LivingEntity::isAlive).forEach(this::touch);
        }
    }

    private void touch(Mob pMob) {
        if (isStinging()) {
            int i = 2;
            if (pMob.hurt(this.damageSources().mobAttack(this), (float) (1 + i))) {
                pMob.addEffect(new MobEffectInstance(MobEffects.POISON, 60 * i, 0), this);
                this.playSound(SoundEvents.PUFFER_FISH_STING, 1.0F, 1.0F);
            }
        }
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void playerTouch(Player pEntity) {
        if (isStinging()) {
            int i = 2;
            if (pEntity instanceof ServerPlayer && pEntity.hurt(this.damageSources().mobAttack(this), 1 + i)) {
                if (!this.isSilent()) {
                    ((ServerPlayer) pEntity).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.PUFFER_FISH_STING, 0.0F));
                }

                pEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 60 * i, 0), this);
            }
        }
    }
}
