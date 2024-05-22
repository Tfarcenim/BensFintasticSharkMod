package tfar.bensfintasticsharkmod.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;
import tfar.bensfintasticsharkmod.entity.GreatWhiteSharkEntityForge;

public class GreatWhiteRenderer extends GeoEntityRenderer<GreatWhiteSharkEntityForge> {
    public GreatWhiteRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(new ResourceLocation(BensFintasticSharkMod.MOD_ID,"great_white"),true));
    }
}
