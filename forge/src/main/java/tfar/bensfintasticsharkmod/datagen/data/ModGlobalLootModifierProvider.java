package tfar.bensfintasticsharkmod.datagen.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import tfar.bensfintasticsharkmod.AddItemChanceLootModifier;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;
import tfar.bensfintasticsharkmod.init.ModItems;

import java.util.HashSet;
import java.util.Set;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output) {
        super(output, BensFintasticSharkMod.MOD_ID);
    }

    // Obtainable through dungeons, shipwrecks, buried treasure, ocean ruins. (Low
    // chance of spawning in them)

    final Set<ResourceLocation> tables = new HashSet<>();

    @Override
    protected void start() {
        tables.add(BuiltInLootTables.SIMPLE_DUNGEON);
        tables.add(BuiltInLootTables.SHIPWRECK_MAP);
        tables.add(BuiltInLootTables.BURIED_TREASURE);
        tables.add(BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY);
        tables.add(BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY);

        for (ResourceLocation table : tables) {
            addModifier(table);
        }
    }

    void addModifier(ResourceLocation table) {
        String[] s = table.getPath().split("/");
        add("add_item_chance_" + s[s.length - 1], new AddItemChanceLootModifier(new LootItemCondition[]{
                LootTableIdCondition.builder(table).build()
        }, ModItems.LOST_MANUSCRIPT, 1, 3, .05f));
    }
}
