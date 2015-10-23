package iceman11a.fuelcraft.tileentity;

import iceman11a.fuelcraft.config.Configs;
import iceman11a.fuelcraft.gui.GuiFuelCraftInventory;
import iceman11a.fuelcraft.gui.GuiTapoilProducer;
import iceman11a.fuelcraft.reference.ReferenceNames;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityTapoilProducer extends TileEntityFluidProcessor
{
	public static int outputFluidProductionRate = 3; // mB per tick; 3 * 20 = 60 mB per second

	public TileEntityTapoilProducer() {
		super(ReferenceNames.NAME_TILE_TAPOIL_PRODUCER, FluidRegistry.getFluid("water"), FluidRegistry.getFluid(ReferenceNames.NAME_FLUID_TAPOIL));
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (this.worldObj.isRemote == false) {
			this.processFluids(Configs.oilProducerEnergyPerOilBucket, Configs.oilProducerTapoilPerOilBucket, outputFluidProductionRate);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public GuiFuelCraftInventory getGui(InventoryPlayer inventoryPlayer) {
		return new GuiTapoilProducer(this.getContainer(inventoryPlayer), this);
	}
}
