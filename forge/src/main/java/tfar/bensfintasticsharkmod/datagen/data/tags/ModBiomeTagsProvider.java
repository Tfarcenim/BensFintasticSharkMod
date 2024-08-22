package tfar.bensfintasticsharkmod.datagen.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;
import tfar.bensfintasticsharkmod.init.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagsProvider extends BiomeTagsProvider {

    public ModBiomeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, BensFintasticSharkMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        // Will spawn in the following biomes
        // 1.Ocean
        // 2. DeepOcean
        tag(ModTags.Biomes.GREAT_WHITE_SHARK_SPAWNS).add(Biomes.OCEAN,Biomes.DEEP_OCEAN);

        //Will spawn in the following biomes
        // 1. Ocean
        // 2. DeepOcean
        // 3. WarmOcean
        // 4. Lukewarm Ocean
        // 5. DeepLukewarm Ocean
        tag(ModTags.Biomes.GREAT_HAMMERHEAD_SHARK_SPAWNS).add(Biomes.OCEAN,Biomes.DEEP_OCEAN,Biomes.WARM_OCEAN,Biomes.LUKEWARM_OCEAN,Biomes.DEEP_LUKEWARM_OCEAN);

        // Will spawn in the following biomes
        // 1.Ocean
        // 2. DeepOcean
        tag(ModTags.Biomes.COMMON_THRESHER_SHARK_SPAWNS).add(Biomes.OCEAN,Biomes.DEEP_OCEAN);

        //Will spawn in the following biomes
        // 1.
        // Beach
        // 2. Ocean
        // 3. DeepOcean
        // 4. Lukewarm Ocean
        // 5. WarmOcean
        // 6. Cold Ocean (Will use Arctic Skin for any cold biomes)
        tag(ModTags.Biomes.HARBOR_SEAL_SPAWNS).add(Biomes.OCEAN,Biomes.DEEP_OCEAN,Biomes.WARM_OCEAN,
                Biomes.LUKEWARM_OCEAN,Biomes.WARM_OCEAN,Biomes.COLD_OCEAN);

    }
}
