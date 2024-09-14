package tfar.bensfintasticsharks.datagen.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import tfar.bensfintasticsharks.BensFintasticSharks;
import tfar.bensfintasticsharks.TextComponents;
import tfar.bensfintasticsharks.advancmenets.PlayerFoundEntityTrigger;
import tfar.bensfintasticsharks.entity.CommonThresherSharkEntity;
import tfar.bensfintasticsharks.entity.GreatHammerheadSharkEntity;
import tfar.bensfintasticsharks.entity.GreatWhiteSharkEntity;
import tfar.bensfintasticsharks.init.EntityVariantPredicates;
import tfar.bensfintasticsharks.init.ModEntityTypes;
import tfar.bensfintasticsharks.init.ModItems;
import tfar.bensfintasticsharks.init.ModTags;

import java.util.function.Consumer;

public class BensFintasticSharksAdvancements implements ForgeAdvancementProvider.AdvancementGenerator {

    private static final EntityType<?>[] MOBS_TO_DISCOVER = new EntityType[]{ModEntityTypes.GREAT_WHITE_SHARK,ModEntityTypes.GREAT_HAMMERHEAD_SHARK
    ,ModEntityTypes.COMMON_THRESHER_SHARK};


    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        Advancement root = Advancement.Builder.advancement()
                .display(ModItems.SHARK_TRIDENT, TextComponents.ROOT, TextComponents.ROOT_DESC,
                new ResourceLocation("textures/gui/advancements/backgrounds/adventure.png"),
                FrameType.TASK, false, false, false)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("unlock_right_away", PlayerTrigger.TriggerInstance.tick())
                .save(saver, BensFintasticSharks.id("root").toString());

        EntityPredicate greatWhiteSharkPredicate = EntityPredicate.Builder.entity().of(ModEntityTypes.GREAT_WHITE_SHARK).build();

        Advancement greatWhiteAdvancement = Advancement.Builder.advancement().parent(root)
                .display(ModItems.GREAT_WHITE_SHARK_PIXEL_ART,TextComponents.GREAT_WHITE_ENCOUNTER,TextComponents.GREAT_WHITE_ENCOUNTER_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("player_found_entity", PlayerFoundEntityTrigger.TriggerInstance.located(greatWhiteSharkPredicate))
                .save(saver, BensFintasticSharks.id("great_white_encounter").toString());

        EntityPredicate greatHammerheadSharkPredicate = EntityPredicate.Builder.entity().of(ModEntityTypes.GREAT_HAMMERHEAD_SHARK).build();

        Advancement greatHammerHeadAdvancement = Advancement.Builder.advancement().parent(root)
                .display(ModItems.GREAT_HAMMERHEAD_SHARK_PIXEL_ART,TextComponents.GREAT_HAMMERHEAD_ENCOUNTER,TextComponents.GREAT_HAMMERHEAD_ENCOUNTER_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("player_found_entity", PlayerFoundEntityTrigger.TriggerInstance.located(greatHammerheadSharkPredicate))
                .save(saver, BensFintasticSharks.id("great_hammerhead_encounter").toString());

        EntityPredicate commonThresherPredicate = EntityPredicate.Builder.entity().of(ModEntityTypes.COMMON_THRESHER_SHARK).build();

        Advancement commonThresherAdvancement = Advancement.Builder.advancement().parent(root)
                .display(ModItems.COMMON_THRESHER_SHARK_PIXEL_ART,TextComponents.COMMON_THRESHER_ENCOUNTER,TextComponents.COMMON_THRESHER_ENCOUNTER_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("player_found_entity", PlayerFoundEntityTrigger.TriggerInstance.located(commonThresherPredicate))
                .save(saver, BensFintasticSharks.id("common_thresher_encounter").toString());

        Advancement killedByShark = Advancement.Builder.advancement().parent(root)
                .display(Items.SKELETON_SKULL,TextComponents.SLEEPING_WITH_THE_FISHES, TextComponents.SLEEPING_WITH_THE_FISHES_DESC, null, FrameType.TASK, true, true, true)
                .addCriterion("killed_by_shark", KilledTrigger.TriggerInstance.entityKilledPlayer(EntityPredicate.Builder.entity().of(ModTags.EntityTypes.SHARKS)))
                .save(saver, BensFintasticSharks.id("sleeping_with_the_fishes").toString());


        EntityPredicate harborSealPredicate = EntityPredicate.Builder.entity().of(ModEntityTypes.HARBOR_SEAL).build();

        Advancement harborSealAdvancement = Advancement.Builder.advancement().parent(root)
                .display(ModItems.HARBOR_SEAL_BLOCK,TextComponents.HARBOR_SEAL_ENCOUNTER,TextComponents.HARBOR_SEAL_ENCOUNTER_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("player_found_entity", PlayerFoundEntityTrigger.TriggerInstance.located(harborSealPredicate))
                .save(saver, BensFintasticSharks.id("harbor_seal_encounter").toString());

        Advancement stingrayAttacksPlayer = Advancement.Builder.advancement().parent(root)
                .display(ModItems.STINGRAY_PIXEL_ART, TextComponents.STINGRAY_ATTACKS_PLAYER, TextComponents.STINGRAY_ATTACKS_PLAYER_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("entity_hurt_player",
                        EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer(
                                DamagePredicate.Builder.damageInstance()
                                        .type(DamageSourcePredicate.Builder.damageType()
                                                .direct(EntityPredicate.Builder.entity().of(ModEntityTypes.COMMON_STINGRAY)))))
                .save(saver, BensFintasticSharks.id("stingray_attacks_player").toString());

        EntityPredicate albinoGreatWhitePredicate = EntityPredicate.Builder.entity().of(ModEntityTypes.GREAT_WHITE_SHARK)
                .subPredicate(EntityVariantPredicates.GREAT_WHITE_SHARK.createPredicate(GreatWhiteSharkEntity.Variant.ALBINO)).build();

        EntityPredicate albinoGreatHammerheadPredicate = EntityPredicate.Builder.entity().of(ModEntityTypes.GREAT_HAMMERHEAD_SHARK)
                .subPredicate(EntityVariantPredicates.GREAT_HAMMERHEAD_SHARK.createPredicate(GreatHammerheadSharkEntity.Variant.ALBINO)).build();

        EntityPredicate albinoCommonThresherPredicate = EntityPredicate.Builder.entity().of(ModEntityTypes.COMMON_THRESHER_SHARK)
                .subPredicate(EntityVariantPredicates.COMMON_THRESHER_SHARK.createPredicate(CommonThresherSharkEntity.Variant.ALBINO)).build();

        Advancement albinoAdvancement = Advancement.Builder.advancement().parent(root)
                .display(ModItems.ALBINO,TextComponents.ALBINO_ENCOUNTER,TextComponents.ALBINO_ENCOUNTER_DESC, null, FrameType.TASK, true, true, false)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("player_found_albino_great_white", PlayerFoundEntityTrigger.TriggerInstance.located(albinoGreatWhitePredicate))
                .addCriterion("player_found_albino_hammerhead", PlayerFoundEntityTrigger.TriggerInstance.located(albinoGreatHammerheadPredicate))
                .addCriterion("player_found_albino_thresher", PlayerFoundEntityTrigger.TriggerInstance.located(albinoCommonThresherPredicate))
                .save(saver, BensFintasticSharks.id("albino_encounter").toString());

        Advancement illegalPoaching = Advancement.Builder.advancement().parent(root)
                .display(ModItems.ILLEGAL_POACHING, TextComponents.ILLEGAL_POACHING, TextComponents.ILLEGAL_POACHING_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("player_killed_entity",
                        KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(ModTags.EntityTypes.SHARKS)))
                .save(saver, BensFintasticSharks.id("illegal_poaching").toString());

        Advancement unethical = Advancement.Builder.advancement().parent(harborSealAdvancement)
                .display(ModItems.UNETHICAL, TextComponents.UNETHICAL, TextComponents.UNETHICAL_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("player_killed_entity",
                        KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(ModEntityTypes.HARBOR_SEAL)))
                .save(saver, BensFintasticSharks.id("unethical").toString());

        Advancement justiceForSteve = Advancement.Builder.advancement().parent(root)
                .display(ModItems.JUSTICE_FOR_STEVE, TextComponents.JUSTICE_FOR_STEVE, TextComponents.JUSTICE_FOR_STEVE_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("player_killed_entity",
                        KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(ModEntityTypes.COMMON_STINGRAY)))
                .save(saver, BensFintasticSharks.id("justice_for_steve").toString());

        Advancement.Builder.advancement().parent(root)
                .display(ModItems.PRISMARINE_CHESTPLATE,TextComponents.PRISMARINE_ARMOR,TextComponents.PRISMARINE_ARMOR_DESC, null, FrameType.CHALLENGE, true, true, false)
                .rewards(AdvancementRewards.Builder.experience(100))
                .addCriterion("prismarine_armor", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PRISMARINE_HELMET, ModItems.PRISMARINE_CHESTPLATE, ModItems.PRISMARINE_LEGGINGS,ModItems.PRISMARINE_BOOTS))
                .save(saver, BensFintasticSharks.id("prismarine_armor").toString());


        EntityPredicate zippyPredicate = EntityPredicate.Builder.entity().of(ModEntityTypes.COMMON_THRESHER_SHARK)
                .subPredicate(EntityVariantPredicates.COMMON_THRESHER_SHARK.createPredicate(CommonThresherSharkEntity.Variant.ZIPPY)).build();

        Advancement zippyAdvancement = Advancement.Builder.advancement().parent(root)
                .display(ModItems.ZIPPY_PIXEL_ART,TextComponents.ZIPPY_ENCOUNTER,TextComponents.ZIPPY_ENCOUNTER_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("player_found_entity", PlayerFoundEntityTrigger.TriggerInstance.located(zippyPredicate))
                .save(saver, BensFintasticSharks.id("zippy_encounter").toString());

        EntityPredicate specimen8Predicate = EntityPredicate.Builder.entity().of(ModEntityTypes.GREAT_WHITE_SHARK)
                .subPredicate(EntityVariantPredicates.GREAT_WHITE_SHARK.createPredicate(GreatWhiteSharkEntity.Variant.SPECIMEN_8)).build();

        Advancement specimen8Advancement = Advancement.Builder.advancement().parent(root)
                .display(Items.REDSTONE,TextComponents.SPECIMEN_8_ENCOUNTER,TextComponents.SPECIMEN_8_ENCOUNTER_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("player_found_entity", PlayerFoundEntityTrigger.TriggerInstance.located(specimen8Predicate))
                .save(saver, BensFintasticSharks.id("specimen_8_encounter").toString());

        EntityPredicate deepBluePredicate = EntityPredicate.Builder.entity().of(ModEntityTypes.GREAT_WHITE_SHARK)
                .subPredicate(EntityVariantPredicates.GREAT_WHITE_SHARK.createPredicate(GreatWhiteSharkEntity.Variant.DEEP_BLUE)).build();

        Advancement deepBlueAdvancement = Advancement.Builder.advancement().parent(root)
                .display(ModItems.GREAT_WHITE_SHARK_SPAWN_EGG,TextComponents.DEEP_BLUE_ENCOUNTER,TextComponents.DEEP_BLUE_ENCOUNTER_DESC, null, FrameType.TASK, true, true, false)
                .addCriterion("player_found_entity", PlayerFoundEntityTrigger.TriggerInstance.located(deepBluePredicate))
                .save(saver, BensFintasticSharks.id("deep_blue_encounter").toString());

        Advancement sharkCodex = Advancement.Builder.advancement().parent(root)
                .display(ModItems.SHARK_CODEX,TextComponents.SHARK_CODEX, TextComponents.SHARK_CODEX_DESC, null, FrameType.TASK, false, false, false)
                .addCriterion("shark_codex", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SHARK_CODEX))
                .save(saver, BensFintasticSharks.id("shark_codex").toString());

        Advancement lostManuscript = Advancement.Builder.advancement().parent(root)
                .display(ModItems.LOST_MANUSCRIPT,TextComponents.LOST_MANUSCRIPT, TextComponents.LOST_MANUSCRIPT_DESC, null, FrameType.TASK, false, false, false)
                .addCriterion("lost_manuscript", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.LOST_MANUSCRIPT))
                .save(saver, BensFintasticSharks.id("lost_manuscript").toString());

        Advancement levelSharkCodex = Advancement.Builder.advancement().parent(sharkCodex)
                .display(ModItems.CODEX_VOLUME,TextComponents.LEVEL_SHARK_CODEX, TextComponents.LEVEL_SHARK_CODEX_DESC, null, FrameType.TASK, false, false, false)
                .addCriterion("level_shark_codex", RecipeCraftedTrigger.TriggerInstance.craftedItem(BensFintasticSharks.id("shark_codex")))
                .save(saver, BensFintasticSharks.id("level_shark_codex").toString());

        Advancement sharksGalore = addMobsToDiscover(Advancement.Builder.advancement().parent(sharkCodex)
                .display(ModItems.SHARK_CODEX,TextComponents.SHARKS_GALORE, TextComponents.SHARKS_GALORE_DESC, null, FrameType.CHALLENGE, false, false, false))
                .save(saver, BensFintasticSharks.id("sharks_galore").toString());
    }

    private static Advancement.Builder addMobsToDiscover(Advancement.Builder pBuilder) {
        for(EntityType<?> entitytype : MOBS_TO_DISCOVER) {
            pBuilder.addCriterion(BuiltInRegistries.ENTITY_TYPE.getKey(entitytype).toString(), PlayerFoundEntityTrigger.TriggerInstance.located(EntityPredicate.Builder.entity().of(entitytype).build()));
        }

        return pBuilder;
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