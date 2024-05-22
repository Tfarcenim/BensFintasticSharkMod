package tfar.bensfintasticsharkmod.init;

import net.minecraft.world.entity.EntityType;
import tfar.bensfintasticsharkmod.entity.GreatWhiteSharkEntity;
import tfar.bensfintasticsharkmod.platform.Services;

public class ModEntityTypes {

    public static final EntityType<? extends GreatWhiteSharkEntity> GREAT_WHITE = Services.PLATFORM.registerGreatWhite();

}
