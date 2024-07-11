package tfar.bensfintasticsharkmod.platform;

import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.commons.lang3.tuple.Pair;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;
import tfar.bensfintasticsharkmod.BensFintasticSharkModForge;
import tfar.bensfintasticsharkmod.entity.*;
import tfar.bensfintasticsharkmod.platform.services.IPlatformHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public <F> void registerAll(Class<?> clazz, Registry<? extends F> registry, Class<F> filter) {
        List<Pair<ResourceLocation, Supplier<?>>> list = BensFintasticSharkModForge.registerLater.computeIfAbsent(registry, k -> new ArrayList<>());
        for (Field field : clazz.getFields()) {
            MappedRegistry<? extends F> forgeRegistry = (MappedRegistry<F>) registry;
            forgeRegistry.unfreeze();
            try {
                Object o = field.get(null);
                if (filter.isInstance(o)) {
                    list.add(Pair.of(new ResourceLocation(BensFintasticSharkMod.MOD_ID, field.getName().toLowerCase(Locale.ROOT)), () -> o));
                }
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        }
    }

    @Override
    public EntityType<GreatHammerheadSharkEntity> registerGreatHammerheadShark() {
        return EntityType.Builder.<GreatHammerheadSharkEntity>of(GreatHammerheadSharkEntityForge::new, MobCategory.WATER_CREATURE).sized(1.75f, 1.5f).build("");
    }

    @Override
    public EntityType<GreatWhiteSharkEntity> registerGreatWhite() {
        return EntityType.Builder.<GreatWhiteSharkEntity>of(GreatWhiteSharkEntityForge::new, MobCategory.WATER_CREATURE).sized(2.25f, 1.75f).build("");
    }

    @Override
    public EntityType<HarborSealEntity> registerHarborSeal() {
        return EntityType.Builder.<HarborSealEntity>of(HarborSealEntityForge::new, MobCategory.WATER_CREATURE).sized(2, 2).build("");
    }

    @Override
    public EntityType<CommonStingrayEntity> registerStingray() {
        return EntityType.Builder.<CommonStingrayEntity>of(CommonStingrayEntityForge::new, MobCategory.WATER_CREATURE).sized(2, 2).build("");
    }

    @Override
    public EntityType<CommonThresherSharkEntity> registerThresherShark() {
        return EntityType.Builder.<CommonThresherSharkEntity>of(CommonThresherSharkEntityForge::new, MobCategory.WATER_CREATURE).sized(2, 2).build("");
    }
}