package tfar.bensfintasticsharkmod;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.commons.lang3.tuple.Pair;
import tfar.bensfintasticsharkmod.client.ModClientForge;
import tfar.bensfintasticsharkmod.datagen.ModDatagen;
import tfar.bensfintasticsharkmod.entity.*;
import tfar.bensfintasticsharkmod.init.ModEntityTypes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Mod(BensFintasticSharkMod.MOD_ID)
public class BensFintasticSharkModForge {
    
    public BensFintasticSharkModForge() {
    
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::register);
        bus.addListener(this::attributes);
        bus.addListener(ModDatagen::start);

        if (FMLEnvironment.dist.isClient()) {
            ModClientForge.init(bus);
        }

        // Use Forge to bootstrap the Common mod.
        BensFintasticSharkMod.init();
    }

    private void attributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.GREAT_WHITE, GreatWhiteSharkEntity.createAttributes().build());
        event.put(ModEntityTypes.GREAT_HAMMERHEAD, GreatHammerheadSharkEntity.createAttributes().build());
        event.put(ModEntityTypes.COMMON_THRESHER, CommonThresherSharkEntity.createAttributes().build());
        event.put(ModEntityTypes.HARBOR_SEAL, HarborSealEntity.createAttributes().build());
        event.put(ModEntityTypes.COMMON_STINGRAY, CommonStingrayEntityForge.createAttributes().build());
    }

    public static Map<Registry<?>, List<Pair<ResourceLocation, Supplier<?>>>> registerLater = new HashMap<>();
    private void register(RegisterEvent e) {
        for (Map.Entry<Registry<?>,List<Pair<ResourceLocation, Supplier<?>>>> entry : registerLater.entrySet()) {
            Registry<?> registry = entry.getKey();
            List<Pair<ResourceLocation, Supplier<?>>> toRegister = entry.getValue();
            for (Pair<ResourceLocation,Supplier<?>> pair : toRegister) {
                e.register((ResourceKey<? extends Registry<Object>>)registry.key(),pair.getLeft(),(Supplier<Object>)pair.getValue());
            }
        }
    }

}