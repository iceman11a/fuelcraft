package iceman11a.fuelcraft.inventory;

import iceman11a.fuelcraft.tileentity.TileEntityCartPainter;
import net.minecraft.entity.player.InventoryPlayer;


public class ContainerCartPainter extends ContainerTileEntityInventory
{
    public ContainerCartPainter(InventoryPlayer inventoryPlayer, TileEntityCartPainter te)
    {
        super(inventoryPlayer, te);
        this.addPlayerInventorySlots(8, 94);
    }

    @Override
    protected void addCustomInventorySlots()
    {
        this.addSlotToContainer(new SlotSingleItem(this.inventory, 0,  80, 46)); // Engine filter
        this.addSlotToContainer(new SlotSingleItem(this.inventory, 1,  80, 22)); // Primary color
        this.addSlotToContainer(new SlotSingleItem(this.inventory, 2,  80, 70)); // Secondary color
    }
}
