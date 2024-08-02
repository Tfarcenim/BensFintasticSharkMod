package tfar.bensfintasticsharkmod.client.renderer;

import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;
import tfar.bensfintasticsharkmod.item.PrismarineArmorItem;

public class PrismarineArmorRenderer extends GeoArmorRenderer<PrismarineArmorItem> {
    public PrismarineArmorRenderer() {
        super(new DefaultedItemGeoModel<>(BensFintasticSharkMod.id("armor/prismarine_armor")));
    }
}
