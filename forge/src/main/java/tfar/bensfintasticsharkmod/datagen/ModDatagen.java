package tfar.bensfintasticsharkmod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;
import tfar.bensfintasticsharkmod.datagen.data.BensFintasticSharksAdvancements;
import tfar.bensfintasticsharkmod.datagen.data.ModDataPackProvider;
import tfar.bensfintasticsharkmod.datagen.data.tags.ModBiomeTagsProvider;
import tfar.bensfintasticsharkmod.datagen.data.tags.ModBlockTagsProvider;
import tfar.bensfintasticsharkmod.datagen.data.tags.ModEntityTypeTagsProvider;
import tfar.bensfintasticsharkmod.datagen.data.tags.ModItemTagsProvider;
import tfar.bensfintasticsharkmod.datagen.data.ModLootTableProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class ModDatagen {

    public static void start(GatherDataEvent e) {
        DataGenerator generator = e.getGenerator();
        ExistingFileHelper helper = e.getExistingFileHelper();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = e.getLookupProvider();


        if (e.includeClient()) {
            generator.addProvider(true,new ModLangProvider(output));
            generator.addProvider(true,new ModBlockstateProvider(output,helper));
            generator.addProvider(true,new ModItemModelProvider(output,helper));
        }
        if (e.includeServer()) {
            generator.addProvider(true, ModLootTableProvider.create(output));
            generator.addProvider(true,new ModRecipeProvider(output));

            ModBlockTagsProvider blockTags = new ModBlockTagsProvider(output,lookupProvider, helper);
            generator.addProvider(true,blockTags);

            generator.addProvider(true,new ModItemTagsProvider(output,lookupProvider,blockTags.contentsGetter(),helper));
            generator.addProvider(true,new ModBiomeTagsProvider(output,lookupProvider,helper));

            generator.addProvider(true,new ModEntityTypeTagsProvider(output,lookupProvider,helper));
            generator.addProvider(true,new ForgeAdvancementProvider(output,lookupProvider,helper, List.of(new BensFintasticSharksAdvancements())));
            generator.addProvider(true,new ModDataPackProvider(output,lookupProvider));
        }
    }

    public static Stream<Block> getKnownBlocks() {
        return BensFintasticSharkMod.getKnown(BuiltInRegistries.BLOCK);
    }

    public static Stream<EntityType<?>> getKnownEntityTypes() {
        return BensFintasticSharkMod.getKnown(BuiltInRegistries.ENTITY_TYPE);
    }


}
