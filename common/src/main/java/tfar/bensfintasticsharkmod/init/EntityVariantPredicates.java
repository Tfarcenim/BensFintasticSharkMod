package tfar.bensfintasticsharkmod.init;

import net.minecraft.advancements.critereon.EntityVariantPredicate;
import tfar.bensfintasticsharkmod.entity.*;

import java.util.Optional;

public class EntityVariantPredicates {

    public static final EntityVariantPredicate<GreatWhiteSharkEntity.Variant> GREAT_WHITE = EntityVariantPredicate.create(GreatWhiteSharkEntity.Variant.CODEC, entity -> entity instanceof GreatWhiteSharkEntity shark ? Optional.of(shark.getVariant()) : Optional.empty());
    public static final EntityVariantPredicate<GreatHammerheadSharkEntity.Variant> GREAT_HAMMERHEAD_SHARK = EntityVariantPredicate.create(GreatHammerheadSharkEntity.Variant.CODEC, entity -> entity instanceof GreatHammerheadSharkEntity shark ? Optional.of(shark.getVariant()) : Optional.empty());
    public static final EntityVariantPredicate<CommonThresherSharkEntity.Variant> COMMON_THRESHER_SHARK = EntityVariantPredicate.create(CommonThresherSharkEntity.Variant.CODEC, entity -> entity instanceof CommonThresherSharkEntity greatWhiteShark ? Optional.of(greatWhiteShark.getVariant()) : Optional.empty());
    public static final EntityVariantPredicate<HarborSealEntity.Variant> HARBOR_SEAL = EntityVariantPredicate.create(HarborSealEntity.Variant.CODEC, entity -> entity instanceof HarborSealEntity greatWhiteShark ? Optional.of(greatWhiteShark.getVariant()) : Optional.empty());
    public static final EntityVariantPredicate<CommonStingrayEntity.Variant> COMMON_STINGRAY = EntityVariantPredicate.create(CommonStingrayEntity.Variant.CODEC, entity -> entity instanceof CommonStingrayEntity greatWhiteShark ? Optional.of(greatWhiteShark.getVariant()) : Optional.empty());

}
