package iceman11a.fuelcraft.item;

import iceman11a.fuelcraft.reference.ReferenceNames;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class FuelcraftItems
{
    public static final Item dieselBucket = new ItemDieselBucket();
    public static final Item tapoilBucket = new ItemTapoilBucket();
    public static final Item resource = new ItemOreResource();
    public static final Item token = new ItemToken();

    public static void registerItems()
    {
        GameRegistry.registerItem(dieselBucket, ReferenceNames.NAME_ITEM_DIESEL_BUCKET);
        GameRegistry.registerItem(resource, ReferenceNames.NAME_ITEM_ORE_RESOURCE);
        GameRegistry.registerItem(tapoilBucket, ReferenceNames.NAME_ITEM_TAPOIL_BUCKET);
        GameRegistry.registerItem(token, ReferenceNames.NAME_ITEM_TOKEN);
    }
}
