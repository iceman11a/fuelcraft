package iceman11a.fuelcraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceman11a.fuelcraft.reference.ReferenceTextures;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockTokenTrack extends BlockFuelcraftBase
{
	public BlockTokenTrack(String name, float hardness, Material material)
	{
		super(name, hardness, material);
		this.setLightLevel(0.2F);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		this.blockIcon = iconRegister.registerIcon(ReferenceTextures.getTileName(this.blockName) + ".tokentrack");
		this.icons = new IIcon[1];

		this.icons[0] = iconRegister.registerIcon(ReferenceTextures.getTileName(this.blockName) + ".tokentrack");
		//this.icons[1] = iconRegister.registerIcon(ReferenceTextures.getTileName(this.blockName) + ".corcoal");
		//this.icons[2] = iconRegister.registerIcon(ReferenceTextures.getTileName(this.blockName) + ".redcor");
		//this.icons[3] = iconRegister.registerIcon(ReferenceTextures.getTileName(this.blockName) + ".blackdiamond");
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
