package iceman11a.fuelcraft.inventory;

import iceman11a.fuelcraft.tileentity.TileEntityFuelCraftInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;


public class ContainerTileEntityInventory extends ContainerFuelCraft
{
    protected TileEntityFuelCraftInventory te;

    public ContainerTileEntityInventory(InventoryPlayer inventoryPlayer, TileEntityFuelCraftInventory te)
    {
        super(inventoryPlayer, te);
        this.te = te;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return super.canInteractWith(player) && this.te.isInvalid() == false;
    }
}