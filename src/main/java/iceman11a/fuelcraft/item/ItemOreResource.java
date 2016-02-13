package iceman11a.fuelcraft.item;

import iceman11a.fuelcraft.reference.ReferenceNames;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class ItemOreResource extends ItemFuelcraftBase
{
    @SideOnly(Side.CLIENT)
    protected IIcon iconArray[];

    public ItemOreResource()
    {
        super();
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setUnlocalizedName(ReferenceNames.NAME_ITEM_ORE_RESOURCE);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName(stack) + "." + stack.getItemDamage();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        for (int i = 0; i < 4; i++)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconRegister)
    {
        this.itemIcon = iconRegister.registerIcon(this.getIconString() + ".corbamite");
        this.iconArray = new IIcon[4];

        this.iconArray[0] = iconRegister.registerIcon(this.getIconString() + ".corbamite");
        this.iconArray[1] = iconRegister.registerIcon(this.getIconString() + ".corcoal");
        this.iconArray[2] = iconRegister.registerIcon(this.getIconString() + ".redcor");
        this.iconArray[3] = iconRegister.registerIcon(this.getIconString() + ".blackdiamond");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconIndex(ItemStack stack)
    {
        if (stack.getItemDamage() < 4)
        {
            return this.iconArray[stack.getItemDamage()];
        }

        return this.itemIcon;
    }
}
