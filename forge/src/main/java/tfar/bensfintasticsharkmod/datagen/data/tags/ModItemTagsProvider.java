package tfar.bensfintasticsharkmod.datagen.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput dataGenerator, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator,lookupProvider, blockTagProvider, BensFintasticSharkMod.MOD_ID, existingFileHelper);
    }

    static final ResourceLocation modular_sword = tetra("modular_sword");
    static final ResourceLocation modular_single = tetra("modular_single");
    static final ResourceLocation modular_double = tetra("modular_double");

    static ResourceLocation tetra(String path) {
        return new ResourceLocation("tetra",path);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
    }
}
