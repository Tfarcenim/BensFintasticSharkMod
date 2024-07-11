package tfar.bensfintasticsharkmod.client.renderer;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;
import tfar.bensfintasticsharkmod.client.renderer.layer.ConditionalAutoGlowingLayer;
import tfar.bensfintasticsharkmod.entity.GreatWhiteSharkEntity;
import tfar.bensfintasticsharkmod.entity.GreatWhiteSharkEntityForge;

import java.util.Locale;
import java.util.Map;

public class GreatWhiteRenderer extends GeoEntityRenderer<GreatWhiteSharkEntityForge> {

    private static final Map<GreatWhiteSharkEntity.Variant, ResourceLocation> TEXTURE_BY_TYPE = Util.make(Maps.newHashMap(), (map) -> {
        for(GreatWhiteSharkEntity.Variant variant : GreatWhiteSharkEntity.Variant.values()) {
            map.put(variant,BensFintasticSharkMod.id(String.format(Locale.ROOT, "textures/entity/great_white/%s.png", variant.getName())));
        }
    });


    public GreatWhiteRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(BensFintasticSharkMod.id("great_white"),true));
        addRenderLayer(new ConditionalAutoGlowingLayer<>(this));
    }

    @Override
    public void actuallyRender(PoseStack poseStack, GreatWhiteSharkEntityForge animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      //  if (animatable.isSharkinator()) {
      //      packedLight = 0xffffff;
      //  }
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ResourceLocation getTextureLocation(GreatWhiteSharkEntityForge animatable) {
        return TEXTURE_BY_TYPE.get(animatable.getVariant());
    }


    @Override
    protected float getDeathMaxRotation(GreatWhiteSharkEntityForge entityLivingBaseIn) {
        return 0;
    }

}
