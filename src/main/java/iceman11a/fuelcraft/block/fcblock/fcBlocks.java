package iceman11a.fuelcraft.block.fcblock;

import cpw.mods.fml.common.registry.GameRegistry;
import iceman11a.fuelcraft.fuelcraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import iceman11a.fuelcraft.machines.ReferenceNames;


public class fcBlocks{
	
		
	public static final Block blockCorbamiteOre = new BlockCorbamiteOre(Material.iron);
	
	public static final Block blockCorCoalOre = new BlockCorCoalOre(Material.iron); 
		
	public static void RegisterBlocks(){
	
	GameRegistry.registerBlock(blockCorbamiteOre, "BlockCorbamiteOre");
	GameRegistry.registerBlock(blockCorCoalOre, "BlockCorCoalOre");
	
	}
}
