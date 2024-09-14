package tfar.bensfintasticsharks.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tfar.bensfintasticsharks.init.ModItems;

public class ModClient {

    public static void registerRenderers() {
        registerThrowing(ModItems.SHARK_TRIDENT);
    }

    static void registerThrowing(Item item) {
        ItemProperties.register(item, new ResourceLocation("throwing"), ModClient::unclampedCall);

    }

    private static float unclampedCall(ItemStack stack, ClientLevel clientLevel, LivingEntity living, int i) {
        return living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F;
    }

}
