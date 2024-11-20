package tfar.bensfintasticsharks.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;

public abstract class SmartWaterAnimal<T extends SmartWaterAnimal<T>> extends WaterAnimal implements SmartBrainOwner<T> {
    protected SmartWaterAnimal(EntityType<T> $$0, Level $$1) {
        super($$0, $$1);
    }

    @Override
    protected Brain.Provider<T> brainProvider() {
        return new SmartBrainProvider<>((T)this);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        tickBrain((T) this);
    }

}
