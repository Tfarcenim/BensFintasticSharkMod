package tfar.bensfintasticsharkmod.init;

import net.minecraft.world.entity.EntityType;
import tfar.bensfintasticsharkmod.entity.*;
import tfar.bensfintasticsharkmod.platform.Services;

public class ModEntityTypes {

    public static final EntityType<? extends GreatWhiteSharkEntity> GREAT_WHITE = Services.PLATFORM.registerGreatWhite();
    public static final EntityType<? extends GreatHammerheadSharkEntity> GREAT_HAMMERHEAD = Services.PLATFORM.registerGreatHammerhead();
    public static final EntityType<? extends CommonThresherSharkEntity> COMMON_THRESHER = Services.PLATFORM.registerThresherShark();
    public static final EntityType<? extends HarborSealEntity> HARBOR_SEAL = Services.PLATFORM.registerHarborSeal();
    public static final EntityType<? extends CommonStingrayEntity> COMMON_STINGRAY = Services.PLATFORM.registerStingray();

}
