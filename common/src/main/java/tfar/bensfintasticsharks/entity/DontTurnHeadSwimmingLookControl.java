package tfar.bensfintasticsharks.entity;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;

public class DontTurnHeadSwimmingLookControl extends SmoothSwimmingLookControl {
    public DontTurnHeadSwimmingLookControl(Mob $$0, int $$1) {
        super($$0, $$1);
    }

    @Override
    public void tick() {
        if (this.lookAtCooldown > 0) {
            --this.lookAtCooldown;
            this.getYRotD().ifPresent(($$0x) -> {
          //      this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, $$0x + 20.0F, this.yMaxRotSpeed);
            });
            this.getXRotD().ifPresent(($$0x) -> {
                this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), $$0x + 10.0F, this.xMaxRotAngle));
            });
        } else {
            if (this.mob.getNavigation().isDone()) {
                this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), 0.0F, 5.0F));
            }

         //   this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, this.mob.yBodyRot, this.yMaxRotSpeed);
        }

        float $$0 = Mth.wrapDegrees(this.mob.yHeadRot - this.mob.yBodyRot);
        if ($$0 < (float)(-this.maxYRotFromCenter)) {
            mob.yBodyRot -= 4.0F;
        } else if ($$0 > (float)this.maxYRotFromCenter) {
            mob.yBodyRot += 4.0F;
        }
    }
}
