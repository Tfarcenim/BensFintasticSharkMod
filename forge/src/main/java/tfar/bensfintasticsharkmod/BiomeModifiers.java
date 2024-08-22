package tfar.bensfintasticsharkmod;

import net.minecraft.resources.ResourceKey;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomeModifiers {
    public static final ResourceKey<BiomeModifier> GREAT_WHITE_SHARK_SPAWNS = create("great_white_shark_spawns");
    public static final ResourceKey<BiomeModifier> GREAT_HAMMERHEAD_SHARK_SPAWNS = create("great_hammerhead_shark_spawns");
    public static final ResourceKey<BiomeModifier> COMMON_THRESHER_SHARK_SPAWNS = create("common_thresher_shark_spawns");
    public static final ResourceKey<BiomeModifier> COMMON_STINGRAY_SHARK_SPAWNS = create("common_stingray_spawns");
    public static final ResourceKey<BiomeModifier> HARBOR_SEAL_SPAWNS = create("harbor_seal_spawns");

    private static ResourceKey<BiomeModifier> create(String key) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,BensFintasticSharkMod.id(key));
    }
}
