package tfar.bensfintasticsharkmod.client.renderer;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;
import tfar.bensfintasticsharkmod.entity.GreatHammerheadSharkEntity;
import tfar.bensfintasticsharkmod.entity.GreatHammerheadSharkEntityForge;
import tfar.bensfintasticsharkmod.entity.GreatWhiteSharkEntity;
import tfar.bensfintasticsharkmod.entity.GreatWhiteSharkEntityForge;

import java.util.Locale;
import java.util.Map;

public class GreatHammerheadRenderer extends GeoEntityRenderer<GreatHammerheadSharkEntityForge> {

    private static final Map<GreatHammerheadSharkEntity.Variant, ResourceLocation> TEXTURE_BY_TYPE = Util.make(Maps.newHashMap(), (map) -> {
        for(GreatHammerheadSharkEntity.Variant variant : GreatHammerheadSharkEntity.Variant.values()) {
            map.put(variant, BensFintasticSharkMod.id(String.format(Locale.ROOT, "textures/entity/great_hammerhead_shark/%s.png", variant.getName())));
        }
    });


    public GreatHammerheadRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(BensFintasticSharkMod.id("great_hammerhead_shark"),true));
    }

    @Override
    public ResourceLocation getTextureLocation(GreatHammerheadSharkEntityForge animatable) {
        return TEXTURE_BY_TYPE.get(animatable.getVariant());
    }
}
