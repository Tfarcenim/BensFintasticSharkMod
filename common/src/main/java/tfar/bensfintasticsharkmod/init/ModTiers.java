package tfar.bensfintasticsharkmod.init;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class ModTiers {

    public static Tier SHARK = new Tier() {
        @Override
        public int getUses() {
            return 251;
        }

        @Override
        public float getSpeed() {
            return 6;
        }

        @Override
        public float getAttackDamageBonus() {
            return 2;
        }

        @Override
        public int getLevel() {
            return 2;
        }

        @Override
        public int getEnchantmentValue() {
            return 15;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(ModTags.Items.SHARK_TEETH);
        }
    };

}
