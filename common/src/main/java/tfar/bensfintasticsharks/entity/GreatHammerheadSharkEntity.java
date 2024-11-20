package tfar.bensfintasticsharks.entity;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomSwimTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import tfar.bensfintasticsharks.BensFintasticSharks;
import tfar.bensfintasticsharks.init.ModTags;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.IntFunction;

public class GreatHammerheadSharkEntity extends SmartWaterAnimal<GreatHammerheadSharkEntity> {
    protected GreatHammerheadSharkEntity(EntityType<GreatHammerheadSharkEntity> $$0, Level $$1) {
        super($$0, $$1);

        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 1/8f, 0, false);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(GreatHammerheadSharkEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> DATA_GRAB_TIMER = SynchedEntityData.defineId(GreatHammerheadSharkEntity.class, EntityDataSerializers.INT);


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 110).add(Attributes.MOVEMENT_SPEED, 1.2F).add(Attributes.ATTACK_DAMAGE, 8);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VARIANT, 0);
        entityData.define(DATA_GRAB_TIMER,0);
    }

    public boolean canTarget(LivingEntity target) {
        if (target instanceof GreatHammerheadSharkEntity) return false;
        if (target instanceof Player player && player.isCreative()) return false;
        if (!isInWater()) return false;
        if (target.isDeadOrDying()) return false;
        if (target.getVehicle() == this) return false;

        if (target.getType().is(ModTags.EntityTypes.GREAT_HAMMERHEAD_SHARK_ALWAYS_ATTACKS)) return true;

        if (target.getHealth() / target.getMaxHealth() <= .5) return true;

        return false;
    }


    @Override
    public List<? extends ExtendedSensor<GreatHammerheadSharkEntity>> getSensors() {
        NearbyLivingEntitySensor<GreatHammerheadSharkEntity> nearbyLivingEntitySensor = new NearbyLivingEntitySensor<>();
        nearbyLivingEntitySensor.setPredicate((target, entity) -> canTarget(target));
        return List.of(nearbyLivingEntitySensor, // This tracks nearby entities
                new HurtBySensor<>());
    }

    @Override
    public BrainActivityGroup<GreatHammerheadSharkEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),                      // Have the entity turn to face and look at its current look target
                new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<GreatHammerheadSharkEntity> getIdleTasks() {


        // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<>(      // Run only one of the below behaviours, trying each one in order. Include the generic type because JavaC is silly
                        new TargetOrRetaliate<>()
                                .attackablePredicate(entity -> this.isInWaterOrBubble() && entity.isAlive() && (!(entity instanceof Player player) || !player.isCreative())),            // Set the attack target and walk target based on nearby entities
                        new SetPlayerLookTarget<>(),          // Set the look target for the nearest player
                        new SetRandomLookTarget<>()),         // Set a random look target
                new OneRandomBehaviour<>(                 // Run a random task from the below options
                        new SetRandomSwimTarget<>(),          // Set a random walk target to a nearby position
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60)))); // Do nothing for 1.5->3 seconds
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



    //this starts at bottom center
    protected Vec3 getAttackPosition() {
        Vec3 bottomCenter = position();
        Vec3 center = bottomCenter.add(0,getBbHeight() / 2,0);
        Vec3 look = getLookAngle();
        float scale = 2;
        return center.add(look.x * scale,0,look.z * scale);
    }

    public void grabMob(LivingEntity entity) {
        if (entity == this.getTarget() && !entity.hasPassenger(this) && this.isInWater()) {
            entity.startRiding(this);
            if (entity instanceof ServerPlayer serverPlayer)
                serverPlayer.connection.send(new ClientboundSetPassengersPacket(entity));
        }
        setGrabTimer(BensFintasticSharks.GRAB_TIMER);
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

    @Override
    protected void positionRider(Entity entity, MoveFunction function) {
        Vec3 look = getLookAngle();
        float dist = 2.55f;


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
        int grabTimer = BensFintasticSharks.GRAB_TIMER - getGrabTimer();

        int mod = grabTimer;//(grabTimer + 3) % 40;

            float degrees = mod * 18;
            float angle =  35 * Mth.sin((float) (degrees * Math.PI /180));
            return angle;

        //.25 - .1 full wave

     }

    void setGrabTimer(int timer) {
        entityData.set(DATA_GRAB_TIMER,timer);
    }

    int getGrabTimer() {
        return entityData.get(DATA_GRAB_TIMER);
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

    public Variant getVariant() {
        return Variant.byId(this.entityData.get(DATA_VARIANT));
    }

    public void setVariant(Variant pVariant) {
        this.entityData.set(DATA_VARIANT, pVariant.getId());
    }

    //i. Default Skin 1 (Common Spawn Rate)
    //ii. Default Skin 2 (Common Spawn Rate)
    //iii. Default Skin 3 (Common Spawn Rate)
    //iv. Default Skin 4 (Common Spawn Rate)
    //v. Melanistic (Extremely Rare Spawn Rate)
    //vi. Albino (Rarest Spawn Rate)

    public enum Variant implements StringRepresentable {
        DEFAULT_1(0, "default_1"),
        DEFAULT_2(1, "default_2"),
        DEFAULT_3(2, "default_3"),
        DEFAULT_4(3, "default_4"),
        MELANISTIC(4, "melanistic"),
        ALBINO(5, "albino");

        private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(Variant::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final Codec<Variant> CODEC = StringRepresentable.fromEnum(Variant::values);

        private static final SimpleWeightedRandomList<Variant> NATURAL_VARIANTS = SimpleWeightedRandomList.<Variant>builder()
                .add(DEFAULT_1,1000)
                .add(DEFAULT_2,1000)
                .add(DEFAULT_3,1000)
                .add(DEFAULT_4,1000)
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
