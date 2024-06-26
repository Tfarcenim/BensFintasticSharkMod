package tfar.bensfintasticsharkmod.client.renderer;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;
import tfar.bensfintasticsharkmod.entity.CommonStingrayEntity;
import tfar.bensfintasticsharkmod.entity.CommonStingrayEntityForge;

import java.util.Locale;
import java.util.Map;

public class CommonStingrayRenderer extends GeoEntityRenderer<CommonStingrayEntityForge> {

    private static final Map<CommonStingrayEntity.Variant, ResourceLocation> TEXTURE_BY_TYPE = Util.make(Maps.newHashMap(), (map) -> {
        for(CommonStingrayEntity.Variant variant : CommonStingrayEntity.Variant.values()) {
            map.put(variant,BensFintasticSharkMod.id(String.format(Locale.ROOT, "textures/entity/common_stingray/%s.png", variant.getName())));
        }
    });


    public CommonStingrayRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(BensFintasticSharkMod.id("great_white"),true));
    }

    @Override
    public ResourceLocation getTextureLocation(CommonStingrayEntityForge animatable) {
        return TEXTURE_BY_TYPE.get(animatable.getVariant());
    }
}
