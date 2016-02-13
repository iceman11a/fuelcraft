package iceman11a.fuelcraft.tileentity;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

public class TileEntityFuelCraftSidedInventory extends TileEntityFuelCraftInventory implements ISidedInventory
{
    public static final int[] SLOTS_EMPTY = new int[0];

    public TileEntityFuelCraftSidedInventory(String name)
    {
        super(name);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side)
    {
        return SLOTS_EMPTY;
    }

    @Override
    public boolean canInsertItem(int slotNum, ItemStack itemStack, int side)
    {
        return super.isItemValidForSlot(slotNum, itemStack);
    }

    @Override
    public boolean canExtractItem(int slotNum, ItemStack itemStack, int side)
    {
        return true;
    }
}
