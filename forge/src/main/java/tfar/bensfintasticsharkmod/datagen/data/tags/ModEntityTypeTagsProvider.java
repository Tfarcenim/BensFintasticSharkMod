package tfar.bensfintasticsharkmod.datagen.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;
import tfar.bensfintasticsharkmod.init.ModEntityTypes;
import tfar.bensfintasticsharkmod.init.ModTags;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public ModEntityTypeTagsProvider(PackOutput p_126511_, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_126511_,lookupProvider, BensFintasticSharkMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        tag(ModTags.EntityTypes.GREAT_WHITE_SHARK_ALWAYS_ATTACK)
                .add(EntityType.COD,EntityType.DOLPHIN,EntityType.GLOW_SQUID,EntityType.PIG,EntityType.SALMON,EntityType.SHEEP,EntityType.SQUID,
                        EntityType.TURTLE,
                        ModEntityTypes.HARBOR_SEAL);
    }
}
