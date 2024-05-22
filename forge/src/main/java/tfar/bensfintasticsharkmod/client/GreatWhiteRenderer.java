package tfar.bensfintasticsharkmod.client;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;
import tfar.bensfintasticsharkmod.entity.GreatWhiteSharkEntity;
import tfar.bensfintasticsharkmod.entity.GreatWhiteSharkEntityForge;

import java.util.Locale;
import java.util.Map;

public class GreatWhiteRenderer extends GeoEntityRenderer<GreatWhiteSharkEntityForge> {

    private static final Map<GreatWhiteSharkEntity.Variant, ResourceLocation> TEXTURE_BY_TYPE = Util.make(Maps.newHashMap(), (p_242076_) -> {
        for(GreatWhiteSharkEntity.Variant variant : GreatWhiteSharkEntity.Variant.values()) {
            p_242076_.put(variant, new ResourceLocation(BensFintasticSharkMod.MOD_ID,String.format(Locale.ROOT, "textures/entity/great_white/%s.png", variant.getName())));
        }

    });


    public GreatWhiteRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(new ResourceLocation(BensFintasticSharkMod.MOD_ID,"great_white"),true));
    }

    @Override
    public ResourceLocation getTextureLocation(GreatWhiteSharkEntityForge animatable) {
        return TEXTURE_BY_TYPE.get(animatable.getVariant());
    }
}
