package iceman11a.fuelcraft.gui;

import iceman11a.fuelcraft.inventory.ContainerFluidProcessor;
import iceman11a.fuelcraft.reference.ReferenceNames;
import iceman11a.fuelcraft.tileentity.TileEntityFluidProcessor;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fluids.FluidRegistry;

public class GuiDieselProducer extends GuiFluidProcessor
{
	public GuiDieselProducer(ContainerFluidProcessor container, TileEntityFluidProcessor te) {
		super(container, te, FluidRegistry.getFluid(ReferenceNames.NAME_FLUID_OIL), FluidRegistry.getFluid(ReferenceNames.NAME_FLUID_DIESEL));
	}

	@Override
	protected void addSlotHoveringText(Slot slot, List<String> list) {
		// Hovering over an empty CorCoal slot
		if (slot == this.inventorySlots.getSlot(TileEntityFluidProcessor.SLOT_FUEL))
		{
			list.add(I18n.format("fuelcraft.gui.label.fuel", new Object[0]));
		}
		// Hovering over an empty Oil Bucket input slot
		else if (slot == this.inventorySlots.getSlot(TileEntityFluidProcessor.SLOT_INPUT_FLUID_BUCKET_IN))
		{
			list.add(I18n.format("fuelcraft.gui.label.oilbucket.in", new Object[0]));
		}
		// Hovering over an empty Oil Bucket output slot
		else if (slot == this.inventorySlots.getSlot(TileEntityFluidProcessor.SLOT_INPUT_FLUID_BUCKET_OUT))
		{
			list.add(I18n.format("fuelcraft.gui.label.oilbucket.out", new Object[0]));
		}
		// Hovering over an empty Diesel Bucket input slot
		else if (slot == this.inventorySlots.getSlot(TileEntityFluidProcessor.SLOT_OUTPUT_FLUID_BUCKET_IN))
		{
			list.add(I18n.format("fuelcraft.gui.label.dieselbucket.in", new Object[0]));
		}
		// Hovering over an empty Diesel Bucket output slot
		else if (slot == this.inventorySlots.getSlot(TileEntityFluidProcessor.SLOT_OUTPUT_FLUID_BUCKET_OUT))
		{
			list.add(I18n.format("fuelcraft.gui.label.dieselbucket.out", new Object[0]));
		}
	}
}
