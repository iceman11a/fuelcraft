package iceman11a.fuelcraft.block.fluid;

import iceman11a.fuelcraft.item.FuelcraftItems;
import iceman11a.fuelcraft.reference.ReferenceNames;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.registry.GameRegistry;


public class FuelcraftFluids
{
	public static BlockFluidBaseFuelcraft blockFluidDiesel;
	public static BlockFluidBaseFuelcraft blockFluidTapoil;
	public static Fluid fluidDiesel;
	public static Fluid fluidTapoil;
	

	public static Fluid registerFluid(String fluidName, int density, int lightLevel, int temp, EnumRarity rarity)
	{
		Fluid fluid = new Fluid(fluidName);
		if (FluidRegistry.registerFluid(fluid) == false)
		{
			fluid = FluidRegistry.getFluid(fluidName);
		}
		else
		{
			if (density != 0)
			{
				fluid.setDensity(density);
			}

			if (lightLevel >= 0)
			{
				fluid.setLuminosity(lightLevel);
			}

			fluid.setRarity(rarity);
			//fluid.setUnlocalizedName(fluidName + ".still");
			fluid.setUnlocalizedName(fluidName + ".name");
		}

		return fluid;
	}

	public static Fluid registerFluid(String fluidName, int density, EnumRarity rarity)
	{
		return registerFluid(fluidName, density, -1, -1, rarity);
	}

	public static void registerFluids() {
		
		fluidDiesel = registerFluid(ReferenceNames.NAME_FLUID_DIESEL, 1050, EnumRarity.common);
		blockFluidDiesel = new BlockFluidDiesel(fluidDiesel);
		GameRegistry.registerBlock(blockFluidDiesel, blockFluidDiesel.getUnlocalizedName());
		
		fluidTapoil = registerFluid(ReferenceNames.NAME_FLUID_TAPOIL, 1050, EnumRarity.common);
		blockFluidTapoil = new BlockFluidTapoil(fluidTapoil);
		GameRegistry.registerBlock(blockFluidTapoil, blockFluidTapoil.getUnlocalizedName());
		
		
	}

	public static void registerFluidContainers()
	{
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(FluidRegistry.getFluidStack(ReferenceNames.NAME_FLUID_DIESEL, FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(FuelcraftItems.bucketDieselFuel), new ItemStack(Items.bucket)));
	}
}
