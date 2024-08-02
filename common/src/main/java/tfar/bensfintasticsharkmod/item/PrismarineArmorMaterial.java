package tfar.bensfintasticsharkmod.item;

import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;

public class PrismarineArmorMaterial implements ArmorMaterial {

    public static final ArmorMaterial PRISMARINE = new PrismarineArmorMaterial();
    protected static final EnumMap<ArmorItem.Type,Integer> DEFENSE_MAP = Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.HELMET, 3);
    });

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return 35 * ArmorMaterials.HEALTH_FUNCTION_FOR_TYPE.get(type);
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return DEFENSE_MAP.get(type);
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_DIAMOND;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(Items.PRISMARINE_SHARD);
    }

    @Override
    public String getName() {
        return "prismarine";
    }

    @Override
    public float getToughness() {
        return 1;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}
