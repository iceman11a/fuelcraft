package iceman11a.fuelcraft;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import iceman11a.fuelcraft.fcItems.fcItems;
import iceman11a.fuelcraft.block.Blockfc;
import iceman11a.fuelcraft.block.fcblock.fcBlocks;
import iceman11a.fuelcraft.proxys.ServerProxy;
import iceman11a.fuelcraft.config.ModConfig;
import iceman11a.fuelcraft.events.EventHelper;
import iceman11a.fuelcraft.Util.Details;
import iceman11a.fuelcraft.Util.GameLogger;
import iceman11a.fuelcraft.Util.Recpies;
import iceman11a.fuelcraft.world.Dimension;
import iceman11a.fuelcraft.world.WorldTypesTutorial;
import iceman11a.fuelcraft.world.biomes.ModBiomes;
import iceman11a.fuelcraft.worldgen.OreGeneration;
import iceman11a.fuelcraft.fluid.descelfluid;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import iceman11a.fuelcraft.fluid.fcfluids;
import iceman11a.fuelcraft.gui.GuiHandler;
import iceman11a.fuelcraft.handler.FuelHandler;
import iceman11a.fuelcraft.machines.FCMachines;


@Mod(modid = Details.MID, name =  Details.MODID, version = Details.VERSION)
public class fuelcraft {
	
	
	public static OreGeneration eventWorldGen = new OreGeneration();
	
	@Instance(Details.MODID)
	public static fuelcraft instance;
	public static Logger logger;
	
	@SidedProxy(clientSide="iceman11a.fuelcraft.proxys.ClientProxy", serverSide="iceman11a.fuelcraft.proxys.ServerProxy")
	private static ServerProxy proxy;	
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		
		logger = event.getModLog();
		
		GameLogger.createFolders();
		ModConfig.createTutConfig();
		fcItems.RegisterItems();
		fcBlocks.RegisterBlocks();
		
		fcfluids.RegisterDiesel();
		FCMachines.RegisterBlocks();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		
				
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		
		Blockfc.loadBlocks();		
		ModBiomes.registerWithBiomeDictionary();
		Dimension.registerWorldProvider();
		Dimension.registerDimensions();
		WorldTypesTutorial.addCustomWorldTypes();
		EventHelper.registerEvents();
		
		proxy.registerRenderers(); // This is blank, Nothing in it
		proxy.registerTileEntities();
		
		GameRegistry.registerWorldGenerator(eventWorldGen,  0);
				
		//GameRegistry.addRecipe(new ItemStack(Corbamite, 6), new Object[]{"RCR","","", 'C', Items.coal, 'R', Items.redstone});
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		GameRegistry.registerFuelHandler(new FuelHandler());				
	}
	
	
	public static CreativeTabs tabFuelcraft = new CreativeTabs("tabFuelcraft"){
		@Override
		public Item getTabIconItem(){
			return new ItemStack(fcItems.Corbamite).getItem();
		}
	};
	
	
	@EventHandler
	public static void postLoad(FMLPostInitializationEvent evt){
		Recpies.registerRecpies();
		  
		
		
	}
	
}
