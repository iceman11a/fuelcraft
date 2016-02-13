package iceman11a.fuelcraft.inventory;

import iceman11a.fuelcraft.tileentity.TileEntityTokenController;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerTokenController extends ContainerTileEntityInventory
{
    public ContainerTokenController(InventoryPlayer inventoryPlayer, TileEntityTokenController te)
    {
        super(inventoryPlayer, te);
        this.addPlayerInventorySlots(8, 94);
    }

    @Override
    protected void addCustomInventorySlots()
    {
    }
}
