package iceman11a.fuelcraft.inventory;

import net.minecraft.inventory.IInventory;

public class SlotSingleItem extends SlotGeneric
{
    public SlotSingleItem(IInventory inventory, int slot, int posX, int posY)
    {
        super(inventory, slot, posX, posY);
    }

    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }
}
