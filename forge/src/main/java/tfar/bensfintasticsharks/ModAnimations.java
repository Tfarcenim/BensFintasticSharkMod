package tfar.bensfintasticsharks;

import software.bernie.geckolib.core.animation.RawAnimation;

public class ModAnimations {
    public static final RawAnimation FAST_SWIM = RawAnimation.begin().thenLoop("move.fast_swim");
    public static final RawAnimation DEATH = RawAnimation.begin().thenPlayAndHold("misc.death");
    public static final RawAnimation THRASH = RawAnimation.begin().thenLoop("attack.thrash");
    public static final RawAnimation BEACHED = RawAnimation.begin().thenLoop("misc.beached");
    public static final RawAnimation BEACHED2 = RawAnimation.begin().thenLoop("misc.beached2");

    public static final RawAnimation BASK = RawAnimation.begin().thenLoop("misc.bask");
}
