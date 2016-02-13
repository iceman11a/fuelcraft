package iceman11a.fuelcraft.item;

import iceman11a.fuelcraft.Fuelcraft;
import iceman11a.fuelcraft.reference.ReferenceNames;
import iceman11a.fuelcraft.reference.ReferenceTextures;
import net.minecraft.item.Item;

public class ItemFuelcraftBase extends Item
{
    public String name;

    public ItemFuelcraftBase()
    {
        super();
        this.setCreativeTab(Fuelcraft.tabFuelcraft);
    }

    @Override
    public Item setUnlocalizedName(String name)
    {
        this.name = name;
        this.setTextureName(ReferenceTextures.getItemTextureName(name));
        return super.setUnlocalizedName(ReferenceNames.getPrefixedName(name));
    }
}
