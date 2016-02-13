package iceman11a.fuelcraft.gui;

import iceman11a.fuelcraft.inventory.ContainerTapoilProducer;
import iceman11a.fuelcraft.reference.ReferenceNames;
import iceman11a.fuelcraft.tileentity.TileEntityFluidProcessor;
import iceman11a.fuelcraft.tileentity.TileEntityTapoilProducer;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fluids.FluidRegistry;

public class GuiTapoilProducer extends GuiFluidProcessor
{
    public GuiTapoilProducer(ContainerTapoilProducer container, TileEntityTapoilProducer te)
    {
        super(container, te, FluidRegistry.getFluid("water"), FluidRegistry.getFluid(ReferenceNames.NAME_FLUID_TAPOIL));
        this.maxTemperature = TileEntityTapoilProducer.MAX_TEMPERATURE;
        this.requiredTemperature = TileEntityTapoilProducer.REQUIRED_TEMPERATURE;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float gameTicks, int mouseX, int mouseY)
    {
        super.drawGuiContainerBackgroundLayer(gameTicks, mouseX, mouseY);

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        // Empty sapling slot, draw the slot background
        Slot slot = this.inventorySlots.getSlot(TileEntityTapoilProducer.SLOT_SAPLING);
        if (slot.getStack() == null)
        {
            this.drawTexturedModalRect(x + slot.xDisplayPosition, y + slot.yDisplayPosition, 224, 0, 16, 16);
        }

        // Empty coal slot, draw the slot background
        slot = this.inventorySlots.getSlot(TileEntityTapoilProducer.SLOT_COAL);
        if (slot.getStack() == null)
        {
            this.drawTexturedModalRect(x + slot.xDisplayPosition, y + slot.yDisplayPosition, this.uFuelBackground, this.vFuelBackground, 16, 16);
        }
    }

    @Override
    protected void addSlotHoveringText(Slot slot, List<String> list)
    {
        // Hovering over an empty fuel slot
        if (slot == this.inventorySlots.getSlot(TileEntityFluidProcessor.SLOT_FUEL))
        {
            list.add(I18n.format("fuelcraft.gui.label.fuel", new Object[0]));
            list.add(I18n.format("fuelcraft.gui.label.fuel.insertinfo", new Object[0]));
        }
        // Hovering over an empty Water Bucket input slot
        else if (slot == this.inventorySlots.getSlot(TileEntityFluidProcessor.SLOT_INPUT_FLUID_BUCKET_IN))
        {
            list.add(I18n.format("fuelcraft.gui.label.waterbucket.in.filled", new Object[0]));
        }
        // Hovering over an empty Bucket output slot
        else if (slot == this.inventorySlots.getSlot(TileEntityFluidProcessor.SLOT_INPUT_FLUID_BUCKET_OUT))
        {
            list.add(I18n.format("fuelcraft.gui.label.waterbucket.out.empty", new Object[0]));
        }
        // Hovering over an empty Bucket input slot
        else if (slot == this.inventorySlots.getSlot(TileEntityFluidProcessor.SLOT_OUTPUT_FLUID_BUCKET_IN))
        {
            list.add(I18n.format("fuelcraft.gui.label.tapoilbucket.in.empty", new Object[0]));
        }
        // Hovering over an empty Tapoil Bucket output slot
        else if (slot == this.inventorySlots.getSlot(TileEntityFluidProcessor.SLOT_OUTPUT_FLUID_BUCKET_OUT))
        {
            list.add(I18n.format("fuelcraft.gui.label.tapoilbucket.out.filled", new Object[0]));
        }
        // Hovering over an empty sapling slot
        else if (slot == this.inventorySlots.getSlot(TileEntityTapoilProducer.SLOT_SAPLING))
        {
            list.add(I18n.format("fuelcraft.gui.label.saplings", new Object[0]));
        }
        // Hovering over an empty coal slot
        else if (slot == this.inventorySlots.getSlot(TileEntityTapoilProducer.SLOT_COAL))
        {
            list.add(I18n.format("fuelcraft.gui.label.coalorcharcoal", new Object[0]));
        }
    }
}
