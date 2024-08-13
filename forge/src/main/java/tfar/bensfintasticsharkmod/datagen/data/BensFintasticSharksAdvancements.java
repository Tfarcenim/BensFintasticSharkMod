package tfar.bensfintasticsharkmod.datagen.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;
import tfar.bensfintasticsharkmod.TextComponents;

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

    }
}
