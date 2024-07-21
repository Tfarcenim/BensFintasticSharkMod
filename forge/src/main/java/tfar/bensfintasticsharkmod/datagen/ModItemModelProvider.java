package tfar.bensfintasticsharkmod.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import tfar.bensfintasticsharkmod.BensFintasticSharkMod;
import tfar.bensfintasticsharkmod.init.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, BensFintasticSharkMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        makeOneLayerItem(ModItems.GREAT_WHITE_SHARK_SPAWN_EGG);
        makeOneLayerItem(ModItems.GREAT_HAMMERHEAD_SHARK_SPAWN_EGG);
        makeOneLayerItem(ModItems.COMMON_THRESHER_SPAWN_EGG);
        makeOneLayerItem(ModItems.HARBOR_SEAL_SPAWN_EGG);
        makeOneLayerItem(ModItems.COMMON_STINGRAY_SPAWN_EGG);
    }

    protected ModelFile.ExistingModelFile generated = getExistingFile(mcLoc("item/generated"));
    protected ModelFile.ExistingModelFile template_spawn_egg = getExistingFile(mcLoc("item/template_spawn_egg"));

    protected void makeSimpleBlockItem(Item item, ResourceLocation loc) {
        String s = BuiltInRegistries.ITEM.getKey(item).toString();
        getBuilder(s)
                .parent(getExistingFile(loc));
    }

    protected void makeSimpleBlockItem(Item item) {
        makeSimpleBlockItem(item,BensFintasticSharkMod.id("block/" + BuiltInRegistries.ITEM.getKey(item).getPath()));
    }

    protected void spawnEgg(Item item) {
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();
        getBuilder(path).parent(template_spawn_egg);
    }

    protected void makeOneLayerItem(Item item, ResourceLocation texture) {
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();
        if (existingFileHelper.exists(new ResourceLocation(texture.getNamespace(), "item/" + texture.getPath())
                , PackType.CLIENT_RESOURCES, ".png", "textures")) {
            getBuilder(path).parent(generated)
                    .texture("layer0", new ResourceLocation(texture.getNamespace(), "item/" + texture.getPath()));
        } else {
            System.out.println("no texture for " + item + " found, skipping");
        }
    }

    protected void makeOneLayerItem(Item item) {
        ResourceLocation texture = BuiltInRegistries.ITEM.getKey(item);
        makeOneLayerItem(item, texture);
    }
}
