package iceman11a.fuelcraft.block.ore;

import iceman11a.fuelcraft.block.BlockFuelcraftBase;
import iceman11a.fuelcraft.item.FuelcraftItems;
import iceman11a.fuelcraft.reference.ReferenceTextures;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFuelcraftOre extends BlockFuelcraftBase
{

    public static final int META_CORBAMITE      = 0;
    public static final int META_CORCOAL        = 1;
    public static final int META_REDCOR         = 2;
    public static final int META_BLACK_DIAMOND  = 3;

    public BlockFuelcraftOre(String name, float hardness, Material material)
    {
        super(name, hardness, material);
        this.setLightLevel(0.2F);
    }

    @Override
    public Item getItemDropped(int metadata, Random rand, int fortune)
    {
        return FuelcraftItems.resource;
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random rand)
    {
        return 3 + rand.nextInt(3);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        for (int i = 0; i < 4; i++)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon(ReferenceTextures.getTileName(this.blockName) + ".corbamite");
        this.icons = new IIcon[4];

        this.icons[0] = iconRegister.registerIcon(ReferenceTextures.getTileName(this.blockName) + ".corbamite");
        this.icons[1] = iconRegister.registerIcon(ReferenceTextures.getTileName(this.blockName) + ".corcoal");
        this.icons[2] = iconRegister.registerIcon(ReferenceTextures.getTileName(this.blockName) + ".redcor");
        this.icons[3] = iconRegister.registerIcon(ReferenceTextures.getTileName(this.blockName) + ".blackdiamond");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        if (meta < 4)
        {
            return this.icons[meta];
        }

        return this.blockIcon;
    }
}
