package iceman11a.fuelcraft.fluid;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import iceman11a.fuelcraft.Util.Details;
import iceman11a.fuelcraft.fluid.block.AFBlockFluidBase;
import iceman11a.fuelcraft.fluid.block.BlockFluidDiesel;

public class fcfluids extends Fluid {			
		

	public fcfluids(String fluidName) {
		super(fluidName);
		// TODO Auto-generated constructor stub		
	}

	@Instance(Details.MODID)
	public static fcfluids instance;

	public static AFBlockFluidBase blockFluidDiesel;
	public static Fluid fluidDiesel;

		public static Fluid registerFluid(String fluidName, int density, int lightLevel, int temp, EnumRarity rarity)
	{
		Fluid fluid = new Fluid(fluidName);
		if (FluidRegistry.registerFluid(fluid) == false)
		{
			fluid = FluidRegistry.getFluid(fluidName);
		}
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

		return fluid;
	}

	public static Fluid registerFluid(String fluidName, int density, EnumRarity rarity)
	{
		return registerFluid(fluidName, density, -1, -1, rarity);
	}
	
	public static void RegisterDiesel() {
		
		fluidDiesel = registerFluid("Diesel", 1050, EnumRarity.common);
		blockFluidDiesel = new BlockFluidDiesel(fluidDiesel);
		GameRegistry.registerBlock(blockFluidDiesel, blockFluidDiesel.getUnlocalizedName());
		//RegistryUtils.overwriteEntry(Item.itemRegistry, "minecraft:milk_bucket", Items.milk_bucket);
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(FluidRegistry.getFluidStack("diesel", FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(Items.water_bucket), new ItemStack(Items.bucket)));
	
	}
	
	
}
