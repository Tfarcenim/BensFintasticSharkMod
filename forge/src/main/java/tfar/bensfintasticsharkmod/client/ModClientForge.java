package tfar.bensfintasticsharkmod.client;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import tfar.bensfintasticsharkmod.entity.GreatWhiteSharkEntityForge;
import tfar.bensfintasticsharkmod.init.ModEntityTypes;

public class ModClientForge {

    public static void init(IEventBus bus) {
        bus.addListener(ModClientForge::renderers);
    }

    static void renderers(final EntityRenderersEvent.RegisterRenderers event) {
        EntityRenderers.register((EntityType<GreatWhiteSharkEntityForge>) ModEntityTypes.GREAT_WHITE, GreatWhiteRenderer::new);
    }

}
