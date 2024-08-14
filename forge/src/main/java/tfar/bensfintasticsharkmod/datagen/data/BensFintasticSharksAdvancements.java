package tfar.bensfintasticsharkmod.datagen.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;
import tfar.bensfintasticsharkmod.TextComponents;
import tfar.bensfintasticsharkmod.advancmenets.PlayerFoundEntityTrigger;
import tfar.bensfintasticsharkmod.init.ModEntityTypes;

import java.util.function.Consumer;

public class BensFintasticSharksAdvancements implements ForgeAdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        Advancement advancement = Advancement.Builder.advancement()
                .display(Items.MAP, TextComponents.ROOT, TextComponents.ROOT_DESC,
                new ResourceLocation("textures/gui/advancements/backgrounds/adventure.png"),
                FrameType.TASK, false, false, false)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("unlock_right_away", PlayerTrigger.TriggerInstance.tick())
                .save(saver, BensFintasticSharkMod.id("root").toString());

        EntityPredicate greatWhiteSharkPredicate = EntityPredicate.Builder.entity().of(ModEntityTypes.GREAT_WHITE_SHARK).build();

        Advancement greatWhiteAdvancement = Advancement.Builder.advancement().parent(advancement)
                .display(Blocks.RED_BED,TextComponents.GREAT_WHITE_ENCOUNTER,TextComponents.GREAT_WHITE_ENCOUNTER_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("player_found_entity", PlayerFoundEntityTrigger.TriggerInstance.located(greatWhiteSharkPredicate))
                .save(saver, BensFintasticSharkMod.id("great_white_encounter").toString());

    }
}
//b. “You’re gonna need a bigger boat…” (Encounter a Great White)
//c. “Stop! Hammer Time!” (Encounter a Great Hammerhead)
//d. “Whiplash!” (Encounter a Common Thresher)
//e. “Sleeping with the fishes.” (Killed by a shark)
//f. “Awkward.” (Encounter a Harbor Seal)
//g. “Crankey!” (Get stung by a Common Stingray)
//h. “It's a shiny!” (Encounter an albino variant)
//i. “Illegal Poaching” (Kill a shark)
//j. “Unethical.” (Kill a Harbor Seal)
//k. “Justice for Steve” (Kill a Common Stingray)
//l. “The Sea Dwelling Knight” (Obtain a full set of Prismarine Armor)
//m. “Shark of Zeus” (Discover Zippy)
//n. “I’ll be back.” (Discover Specimen-8)
//o. “Mommy Shark” (Discover Deep Blue)
//17
//p. “Knowledge is power…” (Craft a Shark Codex)
//q. “Lost beneath the waves” (Find a lost manuscript)
//r. “Level Up!” (Combine 9 shark Codex Pages with a Shark Codex)
//s. “Shark Galore!” (Discover every species of sharks)