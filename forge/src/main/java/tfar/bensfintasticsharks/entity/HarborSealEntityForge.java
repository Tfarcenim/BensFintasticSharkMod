package tfar.bensfintasticsharks.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.util.GeckoLibUtil;
import tfar.bensfintasticsharks.ModAnimations;

public class HarborSealEntityForge extends HarborSealEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public HarborSealEntityForge(EntityType<HarborSealEntity> $$0, Level $$1) {
        super($$0, $$1);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, event -> {
            boolean isDead = this.dead || this.isDeadOrDying();
            boolean isFastMoving = getDeltaMovement().lengthSqr() > .01;
            boolean basking = onGround() && !isInWaterOrBubble() && !event.isMoving();
            if (basking) {
                return event.setAndContinue(ModAnimations.BASK3);
            }
            if (event.isMoving() && !isDead) {
                if (isInWaterOrBubble()) {
                    return event.setAndContinue(isFastMoving ? ModAnimations.FAST_SWIM : DefaultAnimations.SWIM);
                } else {
                    return event.setAndContinue(DefaultAnimations.WALK);
                }
            }
            return event.setAndContinue(DefaultAnimations.SWIM);
        })
                .triggerableAnim("death", ModAnimations.DEATH));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        this.triggerAnim("controller", "death");
        if (this.deathTime == 30) {
            this.remove(Entity.RemovalReason.KILLED);
            this.dropExperience();
        }
    }
}
