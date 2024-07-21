package tfar.bensfintasticsharkmod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tfar.bensfintasticsharkmod.init.ModEntityTypes;

public class ThrownSharkTridentEntity extends ThrownTrident {
    public ThrownSharkTridentEntity(EntityType<? extends ThrownTrident> $$0, Level $$1) {
        super($$0, $$1);
    }

    public ThrownSharkTridentEntity(Level $$0, LivingEntity $$1, ItemStack $$2) {
        super($$0, $$1, $$2);
    }

    @Override
    public EntityType<?> getType() {
        return ModEntityTypes.SHARK_TRIDENT;
    }
}
