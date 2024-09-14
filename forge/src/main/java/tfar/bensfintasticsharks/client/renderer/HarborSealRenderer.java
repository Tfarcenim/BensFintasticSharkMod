package tfar.bensfintasticsharks.client.renderer;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tfar.bensfintasticsharks.BensFintasticSharks;
import tfar.bensfintasticsharks.entity.HarborSealEntity;
import tfar.bensfintasticsharks.entity.HarborSealEntityForge;

import java.util.Locale;
import java.util.Map;

public class HarborSealRenderer extends GeoEntityRenderer<HarborSealEntityForge> {

    private static final Map<HarborSealEntity.Variant, ResourceLocation> TEXTURE_BY_TYPE = Util.make(Maps.newHashMap(), (map) -> {
        for(HarborSealEntity.Variant variant : HarborSealEntity.Variant.values()) {
            map.put(variant, BensFintasticSharks.id(String.format(Locale.ROOT, "textures/entity/harbor_seal/%s.png", variant.getName())));
        }
    });


    public HarborSealRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(BensFintasticSharks.id("harbor_seal"),true));
    }

    @Override
    public ResourceLocation getTextureLocation(HarborSealEntityForge animatable) {
        return TEXTURE_BY_TYPE.get(animatable.getVariant());
    }
}
