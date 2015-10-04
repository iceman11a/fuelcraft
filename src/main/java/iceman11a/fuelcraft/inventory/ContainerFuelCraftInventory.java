package iceman11a.fuelcraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import iceman11a.fuelcraft.tileentity.TileEntityFuelCraftInventory;


public class ContainerFuelCraftInventory extends ContainerFuelCraft
{
	
	protected TileEntityFuelCraftInventory te;
	
    public ContainerFuelCraftInventory(InventoryPlayer inventoryPlayer, TileEntityFuelCraftInventory te) {
		super(inventoryPlayer, te);
		
        this.te = te;
		// TODO Auto-generated constructor stub
	}	
    
    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return super.canInteractWith(player) && this.te.isInvalid() == false;
    }
}