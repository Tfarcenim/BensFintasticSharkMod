package tfar.bensfintasticsharkmod.client.renderer;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;
import tfar.bensfintasticsharkmod.entity.CommonThresherSharkEntityForge;
import tfar.bensfintasticsharkmod.entity.CommonThresherSharkEntity;
import tfar.bensfintasticsharkmod.entity.CommonThresherSharkEntityForge;

import java.util.Locale;
import java.util.Map;

public class CommonThresherRenderer extends GeoEntityRenderer<CommonThresherSharkEntityForge> {

    private static final Map<CommonThresherSharkEntity.Variant, ResourceLocation> TEXTURE_BY_TYPE = Util.make(Maps.newHashMap(), (map) -> {
        for(CommonThresherSharkEntity.Variant variant : CommonThresherSharkEntity.Variant.values()) {
            map.put(variant,BensFintasticSharkMod.id(String.format(Locale.ROOT, "textures/entity/common_thresher_shark/%s.png", variant.getName())));
        }
    });


    public CommonThresherRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(BensFintasticSharkMod.id("common_thresher_shark"),true));
    }

    @Override
    public ResourceLocation getTextureLocation(CommonThresherSharkEntityForge animatable) {
        return TEXTURE_BY_TYPE.get(animatable.getVariant());
    }
}
