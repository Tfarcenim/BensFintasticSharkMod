package tfar.bensfintasticsharks.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import tfar.bensfintasticsharks.entity.ThrownSharkTridentEntity;
import tfar.bensfintasticsharks.init.ModItems;

public class ThrownSharkTridentRenderer extends EntityRenderer<ThrownSharkTridentEntity> {
    private final ItemRenderer itemRenderer;

    public ThrownSharkTridentRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        itemRenderer = pContext.getItemRenderer();
    }

    @Override
    public void render(ThrownSharkTridentEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
            this.itemRenderer.renderStatic(new ItemStack(ModItems.SHARK_TRIDENT), ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, pPackedLight, OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pEntity.level(), 0);
        }

    @Override
    public ResourceLocation getTextureLocation(ThrownSharkTridentEntity pEntity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
