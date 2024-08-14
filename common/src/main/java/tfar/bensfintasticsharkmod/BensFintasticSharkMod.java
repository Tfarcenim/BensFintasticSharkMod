package tfar.bensfintasticsharkmod;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tfar.bensfintasticsharkmod.advancmenets.PlayerFoundEntityTrigger;
import tfar.bensfintasticsharkmod.init.ModCreativeTabs;
import tfar.bensfintasticsharkmod.init.ModEntityTypes;
import tfar.bensfintasticsharkmod.init.ModItems;
import tfar.bensfintasticsharkmod.platform.Services;

import java.util.List;

public class BensFintasticSharkMod {

    public static final String MOD_ID = "bensfintasticsharkmod";
    public static final String MOD_NAME = "BensFintasticSharkMod";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static final PlayerFoundEntityTrigger PLAYER_FOUND_ENTITY = CriteriaTriggers.register(new PlayerFoundEntityTrigger());


    public static void init() {
        Services.PLATFORM.registerAll(ModEntityTypes.class, BuiltInRegistries.ENTITY_TYPE, EntityType.class);
        Services.PLATFORM.registerAll(ModItems.class, BuiltInRegistries.ITEM, Item.class);
        Services.PLATFORM.registerAll(ModCreativeTabs.class, BuiltInRegistries.CREATIVE_MODE_TAB, CreativeModeTab.class);
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID,path);
    }

    public static void playerTick(ServerPlayer player) {
        List<LivingEntity> nearby = player.serverLevel().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT,player,player.getBoundingBox().inflate(16));
        for (LivingEntity living : nearby) {
            PLAYER_FOUND_ENTITY.trigger(player,living);
        }
    }

}