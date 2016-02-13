package iceman11a.fuelcraft.inventory;

import iceman11a.fuelcraft.tileentity.TileEntityCartFilter;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerCartFilter extends ContainerTileEntityInventory
{
    public ContainerCartFilter(InventoryPlayer inventoryPlayer, TileEntityCartFilter te)
    {
        super(inventoryPlayer, te);
        this.addPlayerInventorySlots(8, 94);
    }

    @Override
    protected void addCustomInventorySlots()
    {
        this.addSlotToContainer(new SlotSingleItem(this.inventory, 0,  80, 27)); // Cart type filter
        this.addSlotToContainer(new SlotSingleItem(this.inventory, 1,  80, 61)); // Filter item
    }
}
