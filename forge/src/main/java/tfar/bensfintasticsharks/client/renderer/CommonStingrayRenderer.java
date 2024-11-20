package tfar.bensfintasticsharks.client.renderer;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tfar.bensfintasticsharks.BensFintasticSharks;
import tfar.bensfintasticsharks.entity.CommonStingrayEntity;
import tfar.bensfintasticsharks.entity.CommonStingrayEntityForge;

import java.util.Locale;
import java.util.Map;

public class CommonStingrayRenderer extends GeoEntityRenderer<CommonStingrayEntityForge> {

    private static final Map<CommonStingrayEntity.Variant, ResourceLocation> TEXTURE_BY_TYPE = Util.make(Maps.newHashMap(), (map) -> {
        for(CommonStingrayEntity.Variant variant : CommonStingrayEntity.Variant.values()) {
            map.put(variant, BensFintasticSharks.id(String.format(Locale.ROOT, "textures/entity/common_stingray/%s.png", variant.getName())));
        }
    });


    public CommonStingrayRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(BensFintasticSharks.id("common_stingray")));
    }

    @Override
    public ResourceLocation getTextureLocation(CommonStingrayEntityForge animatable) {
        return TEXTURE_BY_TYPE.get(animatable.getVariant());
    }
}
