package tfar.bensfintasticsharkmod.entity;

import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.function.IntFunction;

public class CommonThresherSharkEntity extends WaterAnimal {
    protected CommonThresherSharkEntity(EntityType<? extends WaterAnimal> $$0, Level $$1) {
        super($$0, $$1);
    }

    private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(CommonThresherSharkEntity.class, EntityDataSerializers.INT);

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 1.2F).add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VARIANT, 0);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        RandomSource randomsource = pLevel.getRandom();
        this.setVariant(Variant.getSpawnVariant(randomsource));
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public Variant getVariant() {
        return Variant.byId(this.entityData.get(DATA_VARIANT));
    }

    public void setVariant(Variant pVariant) {
        this.entityData.set(DATA_VARIANT, pVariant.getId());
    }

    //i. Default Skin 1 (Common Spawn Rate)
    //ii. Melanistic (Extremely Rare Spawn Rate)
    //iii. Albino Variant (Rarest Spawn Rate)
    //4
    //iv. Zippy (Easter Egg) Requires Shark Trident and Thunderstorm
    //{Glows in the dark}

    public enum Variant implements StringRepresentable {
        DEFAULT_1(0, "default_1"),
        MELANISTIC(1, "melanistic"),
        ALBINO(2, "albino"),
        ZIPPY(3,"zippy");

        private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(Variant::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final Codec<Variant> CODEC = StringRepresentable.fromEnum(Variant::values);
        private final int id;
        private final String name;

        private static final SimpleWeightedRandomList<Variant> NATURAL_VARIANTS = SimpleWeightedRandomList.<Variant>builder()
                .add(DEFAULT_1,1000)
                .add(MELANISTIC,10)
                .add(ALBINO,5)
                .build();

        Variant(int pId, String pName) {
            this.id = pId;
            this.name = pName;
        }

        public int getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public String getSerializedName() {
            return this.name;
        }

        public static Variant byId(int pId) {
            return BY_ID.apply(pId);
        }


        private static Variant getSpawnVariant(RandomSource pRandom) {
            return NATURAL_VARIANTS.getRandomValue(pRandom).orElseThrow();
        }
    }
}
