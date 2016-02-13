package iceman11a.fuelcraft.gui;

import iceman11a.fuelcraft.inventory.ContainerTileEntityInventory;
import iceman11a.fuelcraft.tileentity.TileEntityCartPainter;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;

public class GuiCartPainter extends GuiFuelCraftInventory
{
    public GuiCartPainter(ContainerTileEntityInventory container, TileEntityCartPainter te)
    {
        super(container, te);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float gameTicks, int mouseX, int mouseY)
    {
        super.drawGuiContainerBackgroundLayer(gameTicks, mouseX, mouseY);

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        // Empty engine filter slot
        Slot slot = this.inventorySlots.getSlot(TileEntityCartPainter.SLOT_ENGINE_FILTER);
        if (slot.getStack() == null)
        {
            this.drawTexturedModalRect(x + slot.xDisplayPosition, y + slot.yDisplayPosition, 177, 1, 16, 16);
        }

        slot = this.inventorySlots.getSlot(TileEntityCartPainter.SLOT_DYE_PRIMARY);
        if (slot.getStack() == null)
        {
            this.drawTexturedModalRect(x + slot.xDisplayPosition, y + slot.yDisplayPosition, 177, 19, 16, 16);
        }

        slot = this.inventorySlots.getSlot(TileEntityCartPainter.SLOT_DYE_SECONDARY);
        if (slot.getStack() == null)
        {
            this.drawTexturedModalRect(x + slot.xDisplayPosition, y + slot.yDisplayPosition, 177, 19, 16, 16);
        }
    }

    @Override
    protected void addSlotHoveringText(Slot slot, List<String> list)
    {
        // Hovering over an empty engine filter slot
        if (slot == this.inventorySlots.getSlot(TileEntityCartPainter.SLOT_ENGINE_FILTER))
        {
            list.add(I18n.format("fuelcraft.gui.label.filter.locomotive", new Object[0]));
        }
        // Hovering over an empty primary color dye slot
        else if (slot == this.inventorySlots.getSlot(TileEntityCartPainter.SLOT_DYE_PRIMARY))
        {
            list.add(I18n.format("fuelcraft.gui.label.dye.primarycolor", new Object[0]));
        }
        // Hovering over an empty secondary color dye slot
        else if (slot == this.inventorySlots.getSlot(TileEntityCartPainter.SLOT_DYE_SECONDARY))
        {
            list.add(I18n.format("fuelcraft.gui.label.dye.secondarycolor", new Object[0]));
        }
    }
}