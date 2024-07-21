package tfar.bensfintasticsharkmod.datagen.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;
import tfar.bensfintasticsharkmod.init.ModItems;
import tfar.bensfintasticsharkmod.init.ModTags;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput dataGenerator, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator,lookupProvider, blockTagProvider, BensFintasticSharkMod.MOD_ID, existingFileHelper);
    }
    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModTags.Items.SHARK_TEETH).add(ModItems.GREAT_WHITE_SHARK_TOOTH,ModItems.GREAT_HAMMERHEAD_SHARK_TOOTH,ModItems.COMMON_THRESHER_SHARK_TOOTH);
        tag(ItemTags.AXES).add(ModItems.SHARK_AXE);
        tag(ItemTags.HOES).add(ModItems.SHARK_HOE);
        tag(ItemTags.PICKAXES).add(ModItems.SHARK_PICKAXE);
        tag(ItemTags.SWORDS).add(ModItems.SHARK_SWORD);
        tag(ItemTags.SHOVELS).add(ModItems.SHARK_SHOVEL);

    }
}
