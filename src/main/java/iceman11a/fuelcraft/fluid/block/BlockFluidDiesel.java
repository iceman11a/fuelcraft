package iceman11a.fuelcraft.fluid.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import iceman11a.fuelcraft.fluid.reference.Textures;
//import fi.dy.masa.additionalfluids.reference.Textures;



public class BlockFluidDiesel extends AFBlockFluidBase {
	
	@SideOnly(Side.CLIENT)
	public IIcon iconFlow;

	public BlockFluidDiesel(Fluid fluid)
	{
		super("diesel", fluid);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		this.blockIcon = iconRegister.registerIcon(Textures.getFluidName("fluid_diesel_still"));
		this.iconFlow = iconRegister.registerIcon(Textures.getFluidName("fluid_diesel_flow")); // TODO
		this.fluid.setIcons(this.blockIcon, this.iconFlow);
	}

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
    	if (side == 0 || side == 1)
    	{
    		return this.blockIcon;
    	}

    	return this.iconFlow;
    }

}
