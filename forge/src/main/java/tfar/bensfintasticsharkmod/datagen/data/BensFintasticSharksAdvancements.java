package tfar.bensfintasticsharkmod.datagen.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;
import tfar.bensfintasticsharkmod.TextComponents;
import tfar.bensfintasticsharkmod.advancmenets.PlayerFoundEntityTrigger;
import tfar.bensfintasticsharkmod.entity.CommonThresherSharkEntity;
import tfar.bensfintasticsharkmod.entity.GreatHammerheadSharkEntity;
import tfar.bensfintasticsharkmod.entity.GreatWhiteSharkEntity;
import tfar.bensfintasticsharkmod.init.EntityVariantPredicates;
import tfar.bensfintasticsharkmod.init.ModEntityTypes;
import tfar.bensfintasticsharkmod.init.ModItems;
import tfar.bensfintasticsharkmod.init.ModTags;

import java.util.function.Consumer;

public class BensFintasticSharksAdvancements implements ForgeAdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        Advancement root = Advancement.Builder.advancement()
                .display(Items.MAP, TextComponents.ROOT, TextComponents.ROOT_DESC,
                new ResourceLocation("textures/gui/advancements/backgrounds/adventure.png"),
                FrameType.TASK, false, false, false)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("unlock_right_away", PlayerTrigger.TriggerInstance.tick())
                .save(saver, BensFintasticSharkMod.id("root").toString());

        EntityPredicate greatWhiteSharkPredicate = EntityPredicate.Builder.entity().of(ModEntityTypes.GREAT_WHITE_SHARK).build();

        Advancement greatWhiteAdvancement = Advancement.Builder.advancement().parent(root)
                .display(Blocks.RED_BED,TextComponents.GREAT_WHITE_ENCOUNTER,TextComponents.GREAT_WHITE_ENCOUNTER_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("player_found_entity", PlayerFoundEntityTrigger.TriggerInstance.located(greatWhiteSharkPredicate))
                .save(saver, BensFintasticSharkMod.id("great_white_encounter").toString());

        EntityPredicate greatHammerheadSharkPredicate = EntityPredicate.Builder.entity().of(ModEntityTypes.GREAT_HAMMERHEAD_SHARK).build();

        Advancement greatHammerHeadAdvancement = Advancement.Builder.advancement().parent(root)
                .display(Blocks.RED_BED,TextComponents.GREAT_HAMMERHEAD_ENCOUNTER,TextComponents.GREAT_HAMMERHEAD_ENCOUNTER_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("player_found_entity", PlayerFoundEntityTrigger.TriggerInstance.located(greatHammerheadSharkPredicate))
                .save(saver, BensFintasticSharkMod.id("great_hammerhead_encounter").toString());

        EntityPredicate commonThresherPredicate = EntityPredicate.Builder.entity().of(ModEntityTypes.COMMON_THRESHER_SHARK).build();

        Advancement commonThresherAdvancement = Advancement.Builder.advancement().parent(root)
                .display(Blocks.RED_BED,TextComponents.COMMON_THRESHER_ENCOUNTER,TextComponents.COMMON_THRESHER_ENCOUNTER_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("player_found_entity", PlayerFoundEntityTrigger.TriggerInstance.located(commonThresherPredicate))
                .save(saver, BensFintasticSharkMod.id("common_thresher_encounter").toString());

        Advancement killedByShark = Advancement.Builder.advancement().parent(root)
                .display(Raid.getLeaderBannerInstance(),TextComponents.SLEEPING_WITH_THE_FISHES, TextComponents.SLEEPING_WITH_THE_FISHES_DESC, null, FrameType.TASK, true, true, true)
                .addCriterion("killed_by_shark", KilledTrigger.TriggerInstance.entityKilledPlayer(EntityPredicate.Builder.entity().of(ModTags.EntityTypes.SHARKS)))
                .save(saver, BensFintasticSharkMod.id("sleeping_with_the_fishes").toString());


        EntityPredicate harborSealPredicate = EntityPredicate.Builder.entity().of(ModEntityTypes.HARBOR_SEAL).build();

        Advancement harborSealAdvancement = Advancement.Builder.advancement().parent(root)
                .display(Blocks.RED_BED,TextComponents.HARBOR_SEAL_ENCOUNTER,TextComponents.HARBOR_SEAL_ENCOUNTER_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("player_found_entity", PlayerFoundEntityTrigger.TriggerInstance.located(harborSealPredicate))
                .save(saver, BensFintasticSharkMod.id("harbor_seal_encounter").toString());

        Advancement stingrayAttacksPlayer = Advancement.Builder.advancement().parent(root)
                .display(Items.BOW, TextComponents.STINGRAY_ATTACKS_PLAYER, TextComponents.STINGRAY_ATTACKS_PLAYER_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("entity_hurt_player",
                        EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer(
                                DamagePredicate.Builder.damageInstance()
                                        .type(DamageSourcePredicate.Builder.damageType()
                                                .direct(EntityPredicate.Builder.entity().of(ModEntityTypes.COMMON_STINGRAY)))))
                .save(saver, BensFintasticSharkMod.id("stingray_attacks_player").toString());

        EntityPredicate albinoGreatWhitePredicate = EntityPredicate.Builder.entity().of(ModEntityTypes.GREAT_WHITE_SHARK)
                .subPredicate(EntityVariantPredicates.GREAT_WHITE_SHARK.createPredicate(GreatWhiteSharkEntity.Variant.ALBINO)).build();

        EntityPredicate albinoGreatHammerheadPredicate = EntityPredicate.Builder.entity().of(ModEntityTypes.GREAT_HAMMERHEAD_SHARK)
                .subPredicate(EntityVariantPredicates.GREAT_HAMMERHEAD_SHARK.createPredicate(GreatHammerheadSharkEntity.Variant.ALBINO)).build();

        EntityPredicate albinoCommonThresherPredicate = EntityPredicate.Builder.entity().of(ModEntityTypes.COMMON_THRESHER_SHARK)
                .subPredicate(EntityVariantPredicates.COMMON_THRESHER_SHARK.createPredicate(CommonThresherSharkEntity.Variant.ALBINO)).build();

        Advancement albinoAdvancement = Advancement.Builder.advancement().parent(root)
                .display(Blocks.RED_BED,TextComponents.ALBINO_ENCOUNTER,TextComponents.ALBINO_ENCOUNTER_DESC, null, FrameType.TASK, true, true, false)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("player_found_albino_great_white", PlayerFoundEntityTrigger.TriggerInstance.located(albinoGreatWhitePredicate))
                .addCriterion("player_found_albino_hammerhead", PlayerFoundEntityTrigger.TriggerInstance.located(albinoGreatHammerheadPredicate))
                .addCriterion("player_found_albino_thresher", PlayerFoundEntityTrigger.TriggerInstance.located(albinoCommonThresherPredicate))
                .save(saver, BensFintasticSharkMod.id("albino_encounter").toString());

        Advancement illegalPoaching = Advancement.Builder.advancement().parent(root)
                .display(Items.PAPER, TextComponents.ILLEGAL_POACHING, TextComponents.ILLEGAL_POACHING_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("player_killed_entity",
                        KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(ModTags.EntityTypes.SHARKS)))
                .save(saver, BensFintasticSharkMod.id("illegal_poaching").toString());

        Advancement unethical = Advancement.Builder.advancement().parent(harborSealAdvancement)
                .display(Items.PAPER, TextComponents.UNETHICAL, TextComponents.UNETHICAL_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("player_killed_entity",
                        KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(ModEntityTypes.HARBOR_SEAL)))
                .save(saver, BensFintasticSharkMod.id("unethical").toString());

        Advancement justiceForSteve = Advancement.Builder.advancement().parent(root)
                .display(Items.PAPER, TextComponents.JUSTICE_FOR_STEVE, TextComponents.JUSTICE_FOR_STEVE_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("player_killed_entity",
                        KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(ModEntityTypes.COMMON_STINGRAY)))
                .save(saver, BensFintasticSharkMod.id("justice_for_steve").toString());

        Advancement.Builder.advancement().parent(root)
                .display(ModItems.PRISMARINE_CHESTPLATE,TextComponents.PRISMARINE_ARMOR,TextComponents.PRISMARINE_ARMOR_DESC, null, FrameType.CHALLENGE, true, true, false)
                .rewards(AdvancementRewards.Builder.experience(100))
                .addCriterion("prismarine_armor", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PRISMARINE_HELMET, ModItems.PRISMARINE_CHESTPLATE, ModItems.PRISMARINE_LEGGINGS,ModItems.PRISMARINE_BOOTS))
                .save(saver, BensFintasticSharkMod.id("prismarine_armor").toString());


        EntityPredicate zippyPredicate = EntityPredicate.Builder.entity().of(ModEntityTypes.COMMON_THRESHER_SHARK)
                .subPredicate(EntityVariantPredicates.COMMON_THRESHER_SHARK.createPredicate(CommonThresherSharkEntity.Variant.ZIPPY)).build();

        Advancement zippyAdvancement = Advancement.Builder.advancement().parent(root)
                .display(Blocks.RED_BED,TextComponents.ZIPPY_ENCOUNTER,TextComponents.ZIPPY_ENCOUNTER_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("player_found_entity", PlayerFoundEntityTrigger.TriggerInstance.located(zippyPredicate))
                .save(saver, BensFintasticSharkMod.id("zippy_encounter").toString());

        EntityPredicate specimen8Predicate = EntityPredicate.Builder.entity().of(ModEntityTypes.GREAT_WHITE_SHARK)
                .subPredicate(EntityVariantPredicates.GREAT_WHITE_SHARK.createPredicate(GreatWhiteSharkEntity.Variant.SPECIMEN_8)).build();

        Advancement specimen8Advancement = Advancement.Builder.advancement().parent(root)
                .display(Blocks.RED_BED,TextComponents.SPECIMEN_8_ENCOUNTER,TextComponents.SPECIMEN_8_ENCOUNTER_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("player_found_entity", PlayerFoundEntityTrigger.TriggerInstance.located(specimen8Predicate))
                .save(saver, BensFintasticSharkMod.id("specimen_8_encounter").toString());

        EntityPredicate deepBluePredicate = EntityPredicate.Builder.entity().of(ModEntityTypes.GREAT_WHITE_SHARK)
                .subPredicate(EntityVariantPredicates.GREAT_WHITE_SHARK.createPredicate(GreatWhiteSharkEntity.Variant.DEEP_BLUE)).build();

        Advancement deepBlueAdvancement = Advancement.Builder.advancement().parent(root)
                .display(Blocks.RED_BED,TextComponents.DEEP_BLUE_ENCOUNTER,TextComponents.DEEP_BLUE_ENCOUNTER_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("player_found_entity", PlayerFoundEntityTrigger.TriggerInstance.located(deepBluePredicate))
                .save(saver, BensFintasticSharkMod.id("deep_blue_encounter").toString());

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