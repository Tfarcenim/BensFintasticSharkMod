package tfar.bensfintasticsharkmod.client;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import tfar.bensfintasticsharkmod.client.renderer.*;
import tfar.bensfintasticsharkmod.entity.*;
import tfar.bensfintasticsharkmod.init.ModEntityTypes;

public class ModClientForge {

    public static void init(IEventBus bus) {
        bus.addListener(ModClientForge::renderers);
    }

    static void renderers(final EntityRenderersEvent.RegisterRenderers event) {
        EntityRenderers.register((EntityType<GreatWhiteSharkEntityForge>) ModEntityTypes.GREAT_WHITE, GreatWhiteRenderer::new);
        EntityRenderers.register((EntityType<GreatHammerheadSharkEntityForge>) ModEntityTypes.GREAT_HAMMERHEAD_SHARK, GreatHammerheadRenderer::new);
        EntityRenderers.register((EntityType<CommonThresherSharkEntityForge>) ModEntityTypes.COMMON_THRESHER_SHARK, CommonThresherRenderer::new);
        EntityRenderers.register((EntityType<HarborSealEntityForge>) ModEntityTypes.HARBOR_SEAL, HarborSealRenderer::new);
        EntityRenderers.register((EntityType<CommonStingrayEntityForge>) ModEntityTypes.COMMON_STINGRAY, CommonStingrayRenderer::new);


    }

}
