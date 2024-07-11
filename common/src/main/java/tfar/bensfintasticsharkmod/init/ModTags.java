package tfar.bensfintasticsharkmod.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;

public class ModTags {

    public static class EntityTypes {

        public static final TagKey<EntityType<?>> GREAT_WHITE_SHARK_ALWAYS_ATTACKS = create("great_white_shark/always_attacks");
        public static final TagKey<EntityType<?>> GREAT_HAMMERHEAD_SHARK_ALWAYS_ATTACKS = create("great_hammerhead_shark/always_attacks");
        public static final TagKey<EntityType<?>> COMMON_THRESHER_SHARK_ALWAYS_ATTACKS = create("common_thresher_shark/always_attacks");

        private static TagKey<EntityType<?>> create(String pName) {
            return TagKey.create(Registries.ENTITY_TYPE, BensFintasticSharkMod.id(pName));
        }
    }

}
