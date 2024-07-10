package tfar.bensfintasticsharkmod.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;

public class ModTags {

    public static class EntityTypes {

        public static final TagKey<EntityType<?>> GREAT_WHITE_SHARK_ALWAYS_ATTACK = create("always_attack/great_white_shark");

        private static TagKey<EntityType<?>> create(String pName) {
            return TagKey.create(Registries.ENTITY_TYPE, BensFintasticSharkMod.id(pName));
        }
    }

}
