package tfar.bensfintasticsharkmod.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;

public class ModTags {

    public static class EntityTypes {

        public static final TagKey<EntityType<?>> GREAT_WHITE_SHARK_ALWAYS_ATTACKS = create("great_white_shark/always_attacks");
        public static final TagKey<EntityType<?>> GREAT_HAMMERHEAD_SHARK_ALWAYS_ATTACKS = create("great_hammerhead_shark/always_attacks");
        public static final TagKey<EntityType<?>> COMMON_THRESHER_SHARK_ALWAYS_ATTACKS = create("common_thresher_shark/always_attacks");

        public static final TagKey<EntityType<?>> SHARKS = create("sharks");

        private static TagKey<EntityType<?>> create(String pName) {
            return TagKey.create(Registries.ENTITY_TYPE, BensFintasticSharkMod.id(pName));
        }
    }

    public static class Items {
        public static final TagKey<Item> SHARK_TEETH = create("shark_teeth");

        private static TagKey<Item> create(String pName) {
            return TagKey.create(Registries.ITEM, BensFintasticSharkMod.id(pName));
        }
    }

    public static class Biomes {

        public static TagKey<Biome> GREAT_WHITE_SHARK_SPAWNS = create("great_white_shark_spawns");
        public static TagKey<Biome> GREAT_HAMMERHEAD_SHARK_SPAWNS = create("great_hammerhead_shark_spawns");
        public static TagKey<Biome> COMMON_THRESHER_SHARK_SPAWNS = create("common_thresher_shark_spawns");
        public static TagKey<Biome> COMMON_STINGRAY_SPAWNS = create("common_stingray_spawns");
        public static TagKey<Biome> HARBOR_SEAL_SPAWNS = create("harbor_seal_spawns");

        private static TagKey<Biome> create(String pName) {
            return TagKey.create(Registries.BIOME, BensFintasticSharkMod.id(pName));
        }
    }

}
