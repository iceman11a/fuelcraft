package iceman11a.fuelcraft.machines;

import cpw.mods.fml.common.registry.GameRegistry;
import iceman11a.fuelcraft.fuelcraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import iceman11a.fuelcraft.machines.ReferenceNames;


//public class FCMachines extends Block {
public class FCMachines {
	
	public static final Block BlockDieselProducer = new BlockDieselProducer(ReferenceNames.NAME_TILE_ENTITY_DIESEL_PRODUCER, 1.0f, Material.iron);
	
	protected FCMachines(Material material) {
		//super(material.iron);			
		
	}


	public static void RegisterBlocks(){
		
		GameRegistry.registerBlock(BlockDieselProducer, "BlockDieselProducer");
		
		}
}
