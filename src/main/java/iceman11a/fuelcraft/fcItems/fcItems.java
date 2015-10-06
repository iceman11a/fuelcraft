package iceman11a.fuelcraft.fcItems;

import cpw.mods.fml.common.registry.GameRegistry;
import iceman11a.fuelcraft.fuelcraft;
import iceman11a.fuelcraft.block.fcblock.BlockCorbamiteOre;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
//import iceman11a.fuelcraft.fcitems.Corbamite;

public class fcItems {

	public static Item Corbamite = new Corbamite();
	public static Item BucketDieselFuel = new BucketDieselFuel();
	public static Item CorCoal = new CorCoal();
	
		
	public static void RegisterItems(){
		
		GameRegistry.registerItem(Corbamite, "Corbamite");
		GameRegistry.registerItem(BucketDieselFuel, "BucketDieselFuel");
		GameRegistry.registerItem(CorCoal, "CorCoal");
		
	}
	
}
