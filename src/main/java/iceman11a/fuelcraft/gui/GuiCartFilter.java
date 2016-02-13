package iceman11a.fuelcraft.gui;

import iceman11a.fuelcraft.inventory.ContainerTileEntityInventory;
import iceman11a.fuelcraft.tileentity.TileEntityCartFilter;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;

public class GuiCartFilter extends GuiFuelCraftInventory
{
    public GuiCartFilter(ContainerTileEntityInventory container, TileEntityCartFilter te)
    {
        super(container, te);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float gameTicks, int mouseX, int mouseY)
    {
        super.drawGuiContainerBackgroundLayer(gameTicks, mouseX, mouseY);

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        // Empty cart filter slot
        Slot slot = this.inventorySlots.getSlot(TileEntityCartFilter.SLOT_CART_FILTER);
        if (slot.getStack() == null)
        {
            this.drawTexturedModalRect(x + slot.xDisplayPosition, y + slot.yDisplayPosition, 177, 1, 16, 16);
        }

        // Empty filter item slot
        slot = this.inventorySlots.getSlot(TileEntityCartFilter.SLOT_FILTER_ITEM);
        if (slot.getStack() == null)
        {
            this.drawTexturedModalRect(x + slot.xDisplayPosition, y + slot.yDisplayPosition, 177, 19, 16, 16);
        }
    }

    @Override
    protected void addSlotHoveringText(Slot slot, List<String> list)
    {
        // Hovering over an empty cart filter slot
        if (slot == this.inventorySlots.getSlot(TileEntityCartFilter.SLOT_CART_FILTER))
        {
            list.add(I18n.format("fuelcraft.gui.label.cartfilter.carttypefilter", new Object[0]));
        }
        // Hovering over an empty filter item slot
        else if (slot == this.inventorySlots.getSlot(TileEntityCartFilter.SLOT_FILTER_ITEM))
        {
            list.add(I18n.format("fuelcraft.gui.label.cartfilter.filteritem", new Object[0]));
        }
    }
}
