package iceman11a.fuelcraft.handler;

import cpw.mods.fml.common.IFuelHandler;
import iceman11a.fuelcraft.fcItems.fcItems;
import net.minecraft.item.ItemStack;

public class FuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {				
		if(fuel.getItem() == fcItems.Corbamite) {
			return 900;
		}else if(fuel.getItem() == fcItems.CorCoal) {
			return 400;
		}else
			return 0;	
		
		
		
		
	}
}
