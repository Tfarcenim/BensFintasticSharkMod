package tfar.bensfintasticsharkmod.datagen.data.loot;

import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;
import tfar.bensfintasticsharkmod.datagen.ModDatagen;

public class ModBlockLoot extends VanillaBlockLoot {

    @Override
    protected void generate() {
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModDatagen.getKnownBlocks().toList();
    }
}
