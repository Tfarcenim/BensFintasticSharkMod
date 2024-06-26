package tfar.bensfintasticsharkmod.init;

import net.minecraft.advancements.critereon.EntityVariantPredicate;
import tfar.bensfintasticsharkmod.entity.CommonThresherSharkEntity;
import tfar.bensfintasticsharkmod.entity.GreatHammerheadSharkEntity;
import tfar.bensfintasticsharkmod.entity.GreatWhiteSharkEntity;

import java.util.Optional;

public class EntityVariantPredicates {

    public static final EntityVariantPredicate<GreatWhiteSharkEntity.Variant> GREAT_WHITE = EntityVariantPredicate.create(GreatWhiteSharkEntity.Variant.CODEC, entity -> entity instanceof GreatWhiteSharkEntity shark ? Optional.of(shark.getVariant()) : Optional.empty());
    public static final EntityVariantPredicate<GreatHammerheadSharkEntity.Variant> GREAT_HAMMERHEAD = EntityVariantPredicate.create(GreatHammerheadSharkEntity.Variant.CODEC, entity -> entity instanceof GreatHammerheadSharkEntity shark ? Optional.of(shark.getVariant()) : Optional.empty());
    public static final EntityVariantPredicate<CommonThresherSharkEntity.Variant> COMMON_THRESHER = EntityVariantPredicate.create(CommonThresherSharkEntity.Variant.CODEC, entity -> entity instanceof CommonThresherSharkEntity greatWhiteShark ? Optional.of(greatWhiteShark.getVariant()) : Optional.empty());

}
