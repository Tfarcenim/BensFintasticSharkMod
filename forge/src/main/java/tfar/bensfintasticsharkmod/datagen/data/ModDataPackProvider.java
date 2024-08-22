package tfar.bensfintasticsharkmod.datagen.data;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;
import tfar.bensfintasticsharkmod.init.ModEntityTypes;
import tfar.bensfintasticsharkmod.init.ModTags;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
public class ModDataPackProvider extends DatapackBuiltinEntriesProvider {

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModDataPackProvider::biomeModifiers);
    private static final ResourceKey<BiomeModifier> GREAT_WHITE_SPAWNS =
            ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, BensFintasticSharkMod.id("great_white_spawns"));


    public ModDataPackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(BensFintasticSharkMod.MOD_ID));
    }

    public static void biomeModifiers(BootstapContext<BiomeModifier> context) {
        context.register(GREAT_WHITE_SPAWNS,
                new ForgeBiomeModifiers.AddSpawnsBiomeModifier(context.lookup(Registries.BIOME)
                        .getOrThrow(ModTags.Biomes.GREAT_WHITE_SHARK_SPAWNS), List.of(
                                new MobSpawnSettings.SpawnerData(ModEntityTypes.GREAT_WHITE_SHARK,100,1,4)))
        );
    }
}
