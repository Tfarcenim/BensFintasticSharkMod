package tfar.bensfintasticsharkmod.init;

import net.minecraft.advancements.critereon.EntityVariantPredicate;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import tfar.bensfintasticsharkmod.entity.GreatHammerheadSharkEntity;
import tfar.bensfintasticsharkmod.entity.GreatWhiteSharkEntity;

import java.util.Optional;

public class EntityVariantPredicates {

    public static final EntityVariantPredicate<GreatWhiteSharkEntity.Variant> GREAT_WHITE = EntityVariantPredicate.create(GreatWhiteSharkEntity.Variant.CODEC, (p_262508_) -> {
        Optional<GreatWhiteSharkEntity.Variant> optional;
        if (p_262508_ instanceof GreatWhiteSharkEntity greatWhiteShark) {
            optional = Optional.of(greatWhiteShark.getVariant());
        } else {
            optional = Optional.empty();
        }

        return optional;
    });
}
