package tfar.bensfintasticsharkmod;

import net.minecraftforge.fml.common.Mod;

@Mod(BensFintasticSharkMod.MOD_ID)
public class BensFintasticSharkModForge {
    
    public BensFintasticSharkModForge() {
    
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
    
        // Use Forge to bootstrap the Common mod.
        BensFintasticSharkMod.LOG.info("Hello Forge world!");
        BensFintasticSharkMod.init();
        
    }
}