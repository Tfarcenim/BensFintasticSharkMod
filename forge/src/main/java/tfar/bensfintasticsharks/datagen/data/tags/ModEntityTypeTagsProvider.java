package tfar.bensfintasticsharks.datagen.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import tfar.bensfintasticsharks.BensFintasticSharks;
import tfar.bensfintasticsharks.init.ModEntityTypes;
import tfar.bensfintasticsharks.init.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public ModEntityTypeTagsProvider(PackOutput p_126511_, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_126511_,lookupProvider, BensFintasticSharks.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        tag(ModTags.EntityTypes.GREAT_WHITE_SHARK_ALWAYS_ATTACKS)
                .add(EntityType.COD,EntityType.COW,EntityType.DOLPHIN,EntityType.GLOW_SQUID,EntityType.PIG,EntityType.SALMON,EntityType.SHEEP,EntityType.SQUID,
                        EntityType.TURTLE,
                        ModEntityTypes.HARBOR_SEAL);

        tag(ModTags.EntityTypes.GREAT_HAMMERHEAD_SHARK_ALWAYS_ATTACKS)
                .add(EntityType.COD,EntityType.COW,EntityType.DOLPHIN,EntityType.GLOW_SQUID,EntityType.PIG,EntityType.SALMON,EntityType.SHEEP,EntityType.SQUID,
                        EntityType.TURTLE,
                        ModEntityTypes.HARBOR_SEAL);

        tag(ModTags.EntityTypes.COMMON_THRESHER_SHARK_ALWAYS_ATTACKS)
                .add(EntityType.COD,EntityType.GLOW_SQUID,EntityType.SALMON,EntityType.SHEEP,EntityType.SQUID,
                        EntityType.TURTLE);

        tag(ModTags.EntityTypes.SHARKS)
                .add(ModEntityTypes.COMMON_THRESHER_SHARK,ModEntityTypes.GREAT_HAMMERHEAD_SHARK,ModEntityTypes.GREAT_WHITE_SHARK);
    }
}
