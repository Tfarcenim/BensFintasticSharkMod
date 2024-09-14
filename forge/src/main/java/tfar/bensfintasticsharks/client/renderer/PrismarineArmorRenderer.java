package tfar.bensfintasticsharks.client.renderer;

import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import tfar.bensfintasticsharks.BensFintasticSharks;
import tfar.bensfintasticsharks.item.PrismarineArmorItem;

public class PrismarineArmorRenderer extends GeoArmorRenderer<PrismarineArmorItem> {
    public PrismarineArmorRenderer() {
        super(new DefaultedItemGeoModel<>(BensFintasticSharks.id("armor/prismarine_armor")));
    }
}
