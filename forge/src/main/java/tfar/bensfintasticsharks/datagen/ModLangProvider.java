package tfar.bensfintasticsharks.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import org.codehaus.plexus.util.StringUtils;
import tfar.bensfintasticsharks.BensFintasticSharks;
import tfar.bensfintasticsharks.TextComponents;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput gen) {
        super(gen, BensFintasticSharks.MOD_ID, "en_us");
    }

    protected final Set<Item> exclude_items = new HashSet<>();
    @Override
    protected void addTranslations() {
        BensFintasticSharks.getKnownItems().filter(item -> item instanceof BlockItem).forEach(exclude_items::add);
        BensFintasticSharks.getKnownItems().toList().stream().filter(item -> !exclude_items.contains(item)).<Supplier<? extends Item>>map(item -> () -> item).forEach(this::addDefaultItem);

        for (EntityType<?> type : ModDatagen.getKnownEntityTypes().toList()) {
            addDefaultEntityType(() -> type);
        }

        addTextComponent(TextComponents.ROOT,"Ben’s Fintastic Sharks!");
        addTextComponent(TextComponents.ROOT_DESC,"Achievement granted when logging in with the mod");

        addTextComponent(TextComponents.GREAT_WHITE_ENCOUNTER,"You’re gonna need a bigger boat…");
        addTextComponent(TextComponents.GREAT_WHITE_ENCOUNTER_DESC,"Encounter a Great White Shark");

        addTextComponent(TextComponents.GREAT_HAMMERHEAD_ENCOUNTER,"Stop! Hammer Time!");
        addTextComponent(TextComponents.GREAT_HAMMERHEAD_ENCOUNTER_DESC,"Encounter a Great Hammerhead Shark");

        addTextComponent(TextComponents.COMMON_THRESHER_ENCOUNTER,"Whiplash!");
        addTextComponent(TextComponents.COMMON_THRESHER_ENCOUNTER_DESC,"Encounter a Common Thresher Shark");

        addTextComponent(TextComponents.HARBOR_SEAL_ENCOUNTER,"Awkward.");
        addTextComponent(TextComponents.HARBOR_SEAL_ENCOUNTER_DESC,"Encounter a Harbor Seal");

        addTextComponent(TextComponents.SLEEPING_WITH_THE_FISHES,"Sleeping with the fishes.");
        addTextComponent(TextComponents.SLEEPING_WITH_THE_FISHES_DESC,"Killed by a shark");

        addTextComponent(TextComponents.STINGRAY_ATTACKS_PLAYER,"Crankey!");
        addTextComponent(TextComponents.STINGRAY_ATTACKS_PLAYER_DESC,"Get stung by a Common Stingray");

        addTextComponent(TextComponents.ALBINO_ENCOUNTER,"It's a shiny!");
        addTextComponent(TextComponents.ALBINO_ENCOUNTER_DESC,"Encounter an albino variant");

        addTextComponent(TextComponents.ILLEGAL_POACHING,"Illegal Poaching");
        addTextComponent(TextComponents.ILLEGAL_POACHING_DESC,"Kill a shark");

        addTextComponent(TextComponents.UNETHICAL,"Unethical");
        addTextComponent(TextComponents.UNETHICAL_DESC,"Kill a Harbor Seal");

        addTextComponent(TextComponents.JUSTICE_FOR_STEVE,"Justice for Steve");
        addTextComponent(TextComponents.JUSTICE_FOR_STEVE_DESC,"Kill a Common Stingray");

        addTextComponent(TextComponents.PRISMARINE_ARMOR,"The Sea Dwelling Knight");
        addTextComponent(TextComponents.PRISMARINE_ARMOR_DESC,"Obtain a full set of Prismarine Armor");

        addTextComponent(TextComponents.ZIPPY_ENCOUNTER,"Shark of Zeus");
        addTextComponent(TextComponents.ZIPPY_ENCOUNTER_DESC,"Discover Zippy");

        addTextComponent(TextComponents.SPECIMEN_8_ENCOUNTER,"I’ll be back.");
        addTextComponent(TextComponents.SPECIMEN_8_ENCOUNTER_DESC,"Discover Specimen-8");

        addTextComponent(TextComponents.DEEP_BLUE_ENCOUNTER,"Mommy Shark");
        addTextComponent(TextComponents.DEEP_BLUE_ENCOUNTER_DESC,"Discover Deep Blue");

        addTextComponent(TextComponents.SHARK_CODEX,"Knowledge is power…");
        addTextComponent(TextComponents.SHARK_CODEX_DESC,"Craft a Shark Codex");

        addTextComponent(TextComponents.LOST_MANUSCRIPT,"Lost beneath the waves");
        addTextComponent(TextComponents.LOST_MANUSCRIPT_DESC,"Find a lost manuscript");

        addTextComponent(TextComponents.LEVEL_SHARK_CODEX,"Level Up!");
        addTextComponent(TextComponents.LEVEL_SHARK_CODEX_DESC,"Combine 9 shark Codex Pages with a Shark Codex");

        addTextComponent(TextComponents.SHARKS_GALORE,"Shark Galore!");
        addTextComponent(TextComponents.SHARKS_GALORE_DESC,"Discover every species of sharks");

        addTextComponent(TextComponents.TAB_TITLE,"Ben's Fintastic Sharks");
    }


    protected void addDefaultItem(Supplier<? extends Item> supplier) {
        addItem(supplier,getNameFromItem(supplier.get()));
    }

    protected void addDefaultBlock(Supplier<? extends Block> supplier) {
        addBlock(supplier,getNameFromBlock(supplier.get()));
    }

    protected void addDefaultEnchantment(Supplier<? extends Enchantment> supplier) {
        addEnchantment(supplier,getNameFromEnchantment(supplier.get()));
    }

    protected void addDefaultEntityType(Supplier<EntityType<?>> supplier) {
        addEntityType(supplier,getNameFromEntity(supplier.get()));
    }

    public static String getNameFromItem(Item item) {
        return StringUtils.capitaliseAllWords(item.getDescriptionId().split("\\.")[2].replace("_", " "));
    }

    public static String getNameFromBlock(Block block) {
        return StringUtils.capitaliseAllWords(block.getDescriptionId().split("\\.")[2].replace("_", " "));
    }

    public static String getNameFromEnchantment(Enchantment enchantment) {
        return StringUtils.capitaliseAllWords(enchantment.getDescriptionId().split("\\.")[2].replace("_", " "));
    }

    public static String getNameFromEntity(EntityType<?> entity) {
        return StringUtils.capitaliseAllWords(entity.getDescriptionId().split("\\.")[2].replace("_", " "));
    }

    protected void addTextComponent(MutableComponent component, String text) {
        ComponentContents contents = component.getContents();
        if (contents instanceof TranslatableContents translatableContents) {
            add(translatableContents.getKey(),text);
        } else {
            throw new UnsupportedOperationException(component +" is not translatable");
        }
    }

}
