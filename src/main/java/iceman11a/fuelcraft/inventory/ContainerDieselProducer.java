package iceman11a.fuelcraft.inventory;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
//import iceman11a.fuelcraft.setup.FuelCraftItems;
import iceman11a.fuelcraft.tileentity.TileEntityDieselProducer;


import iceman11a.fuelcraft.tileentity.TileEntityFuelCraftInventory;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerDieselProducer extends ContainerTileEntityInventory {
	
	
	private TileEntityDieselProducer tileEntityDieselProducer;
  
	public ContainerDieselProducer(InventoryPlayer inventoryPlayer, TileEntityDieselProducer te) {		
		
		super(inventoryPlayer, te);
        this.tileEntityDieselProducer = te;
        this.addPlayerInventorySlots(8, 84);		
	}
	
	 @Override
	    protected void addCustomInventorySlots()
	    {
	        this.addSlotToContainer(new SlotGeneric(this.inventory, 0, 34, 17));
	        this.addSlotToContainer(new SlotGeneric(this.inventory, 1, 34, 53));
	        this.addSlotToContainer(new SlotGeneric(this.inventory, 2, 34, 53));
	        this.addSlotToContainer(new SlotGeneric(this.inventory, 3, 34, 53));
	        this.addSlotToContainer(new SlotGeneric(this.inventory, 4, 34, 53));
	    }

	    @Override
	    public void detectAndSendChanges()
	    {
	        super.detectAndSendChanges();

	        
	    }
	    
	    @SideOnly(Side.CLIENT)
	    @Override
	    public void updateProgressBar(int var, int val)
	    {
	        
	    }

	   

}
