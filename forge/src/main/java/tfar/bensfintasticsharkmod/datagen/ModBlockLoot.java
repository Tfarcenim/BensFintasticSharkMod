package tfar.bensfintasticsharkmod.datagen;

import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;

public class ModBlockLoot extends VanillaBlockLoot {

    @Override
    protected void generate() {
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModDatagen.getKnownBlocks().toList();
    }
}
