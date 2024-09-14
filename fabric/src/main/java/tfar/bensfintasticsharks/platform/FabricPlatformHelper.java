package tfar.bensfintasticsharks.platform;

import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import tfar.bensfintasticsharks.entity.*;
import tfar.bensfintasticsharks.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public <F> void registerAll(Class<?> clazz, Registry<? extends F> registry, Class<F> filter) {

    }

    @Override
    public EntityType<GreatHammerheadSharkEntity> registerGreatHammerheadShark() {
        return null;
    }

    @Override
    public EntityType<GreatWhiteSharkEntity> registerGreatWhite() {
        return null;
    }

    @Override
    public EntityType<HarborSealEntity> registerHarborSeal() {
        return null;
    }

    @Override
    public EntityType<CommonStingrayEntity> registerStingray() {
        return null;
    }

    @Override
    public EntityType<CommonThresherSharkEntity> registerThresherShark() {
        return null;
    }

    @Override
    public ArmorItem createPrismarineArmor(ArmorMaterial prismarine, ArmorItem.Type helmet, Item.Properties properties) {
        return null;
    }
}
