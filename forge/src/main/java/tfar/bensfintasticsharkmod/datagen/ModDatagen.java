package tfar.bensfintasticsharkmod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;
import tfar.bensfintasticsharkmod.datagen.data.BensFintasticSharksAdvancements;
import tfar.bensfintasticsharkmod.datagen.data.ModAdvancementProvider;
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
            generator.addProvider(true,new ModEntityTypeTagsProvider(output,lookupProvider,helper));
            generator.addProvider(true,new ModAdvancementProvider(output,lookupProvider,helper, List.of(new BensFintasticSharksAdvancements())));
        }
    }

    public static Stream<Block> getKnownBlocks() {
        return getKnown(BuiltInRegistries.BLOCK);
    }
    public static Stream<Item> getKnownItems() {
        return getKnown(BuiltInRegistries.ITEM);
    }

    public static Stream<EntityType<?>> getKnownEntityTypes() {
        return getKnown(BuiltInRegistries.ENTITY_TYPE);
    }

    public static <V> Stream<V> getKnown(Registry<V> registry) {
        return registry.stream().filter(o -> registry.getKey(o).getNamespace().equals(BensFintasticSharkMod.MOD_ID));
    }


}
