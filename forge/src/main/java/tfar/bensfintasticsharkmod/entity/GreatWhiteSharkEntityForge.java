package tfar.bensfintasticsharkmod.entity;

import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomSwimTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class GreatWhiteSharkEntityForge extends GreatWhiteSharkEntity implements GeoEntity, SmartBrainOwner<GreatWhiteSharkEntityForge> {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected static final RawAnimation FAST_SWIM = RawAnimation.begin().thenLoop("move.fast_swim");
    protected static final RawAnimation DEATH = RawAnimation.begin().thenPlayAndHold("misc.death");

    private static final EntityDataAccessor<Boolean> BITING = SynchedEntityData.defineId(GreatWhiteSharkEntityForge.class, EntityDataSerializers.BOOLEAN);

    protected int bitingTimer;

    public GreatWhiteSharkEntityForge(EntityType<? extends WaterAnimal> $$0, Level $$1) {
        super($$0, $$1);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        boolean isAttacking = this.swinging;
        boolean isDead = this.dead || this.getHealth() < 0.01 || this.isDeadOrDying();
        boolean isFastMoving = getDeltaMovement().lengthSqr() > .01;
        controllers.add(new AnimationController<>(this, "idle_controller", 0, event -> {
            if (event.isMoving() && !isDead && !isAttacking) {
                return event.setAndContinue(isFastMoving ? FAST_SWIM : DefaultAnimations.SWIM);
            }
            return event.setAndContinue(DefaultAnimations.IDLE);
        })
                .triggerableAnim("bite", RawAnimation.begin().then("attack.bite", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("death", DEATH));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(BITING, false);
    }

    public boolean isBiting() {
        return entityData.get(BITING);
    }

    public void setBiting(boolean biting) {
        entityData.set(BITING, biting);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public List<? extends ExtendedSensor<GreatWhiteSharkEntityForge>> getSensors() {
        NearbyLivingEntitySensor<GreatWhiteSharkEntityForge> nearbyLivingEntitySensor = new NearbyLivingEntitySensor<>();
        nearbyLivingEntitySensor.setPredicate((target, entity) -> canTarget(target));
        return List.of(nearbyLivingEntitySensor, // This tracks nearby entities
                new HurtBySensor<>());
    }

    public boolean canTarget(LivingEntity target) {
        if (target instanceof GreatWhiteSharkEntity) return false;
        if (!isInWater()) return false;
        if (target.getHealth() / target.getMaxHealth() <= .5) return true;

        if (!target.isInWater()) return false;

        EntityDimensions dimensions = target.getDimensions(Pose.STANDING);
        return target.isAlive();
    }

    @Override
    public BrainActivityGroup<? extends GreatWhiteSharkEntityForge> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),                      // Have the entity turn to face and look at its current look target
                new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<GreatWhiteSharkEntityForge> getIdleTasks() {
        // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<>(      // Run only one of the below behaviours, trying each one in order. Include the generic type because JavaC is silly
                        new TargetOrRetaliate<>(),            // Set the attack target and walk target based on nearby entities
                        new SetPlayerLookTarget<>(),          // Set the look target for the nearest player
                        new SetRandomLookTarget<>()),         // Set a random look target
                new OneRandomBehaviour<>(                 // Run a random task from the below options
                        new SetRandomSwimTarget<>(),          // Set a random walk target to a nearby position
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60)))); // Do nothing for 1.5->3 seconds
    }

    @Override
    public BrainActivityGroup<GreatWhiteSharkEntityForge> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(), // Cancel fighting if the target is no longer valid
                new SetWalkTargetToAttackTarget<>().speedMod((owner, target) -> 2.5f),      // Set the walk target to the attack target


                new OneRandomBehaviour<>(
                        new AnimatableMeleeAttack<>(4) {
                            @Override
                            protected void start(Mob entity) {
                                BehaviorUtils.lookAtEntity(entity, this.target);
                                bitingTimer = 12;
                                GreatWhiteSharkEntityForge.this.triggerAnim("idle_controller", "bite");

                            }
                        }
                      /* , new AnimatableMeleeAttack<>(4) {
                            @Override
                            protected void start(Mob entity) {
                                BehaviorUtils.lookAtEntity(entity, this.target);
                                bitingTimer = 12;
                                setBiting(true);
                            }
                        }*/)); // Melee attack the target if close enough
    }

    public void grabMob(LivingEntity entity) {
        if (entity == this.getTarget() && !entity.hasPassenger(this) && this.isInWater()) {
            entity.startRiding(this);
            if (entity instanceof ServerPlayer serverPlayer)
               serverPlayer.connection.send(new ClientboundSetPassengersPacket(entity));
        }
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        tickBrain(this);
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        return new WaterBoundPathNavigation(this, pLevel) {
            @Override
            protected boolean canUpdatePath() {
                return true;
            }
        };
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        this.triggerAnim("idle_controller", "death");
        if (this.deathTime == 35) {
            this.remove(Entity.RemovalReason.KILLED);
            this.dropExperience();
        }
    }
}
