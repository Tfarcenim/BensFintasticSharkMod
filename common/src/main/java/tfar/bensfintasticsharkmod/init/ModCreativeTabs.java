package tfar.bensfintasticsharkmod.init;

import net.minecraft.world.item.CreativeModeTab;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;
import tfar.bensfintasticsharkmod.TextComponents;
import tfar.bensfintasticsharkmod.item.HiddenItem;

public class ModCreativeTabs {

    public static final CreativeModeTab TAB = CreativeModeTab.builder(null,-1)
            .title(TextComponents.TAB_TITLE)
            .displayItems((itemDisplayParameters, output) -> {
                BensFintasticSharkMod.getKnownItems().filter(item -> !(item instanceof HiddenItem)).forEach(output::accept);
            })
            .icon(ModItems.GREAT_HAMMERHEAD_SHARK_SPAWN_EGG::getDefaultInstance).build();

}
