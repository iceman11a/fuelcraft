package iceman11a.fuelcraft.tileentity;

import iceman11a.fuelcraft.config.Configs;
import iceman11a.fuelcraft.gui.GuiDieselProducer;
import iceman11a.fuelcraft.gui.GuiFuelCraftInventory;
import iceman11a.fuelcraft.reference.ReferenceNames;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityDieselProducer extends TileEntityFluidProcessor
{
    public static final int[] SLOTS_NOT_FUEL = new int[] {1, 2, 3, 4};

    public static final int MAX_TEMPERATURE = 200;
    public static final int REQUIRED_TEMPERATURE = 175;

    public static int heatingEnergyperTick = 20;
    public static int heatMaintainEnergyperTick = 5;
    public static float heatingSpeed = MAX_TEMPERATURE / (120F * 20F); // 120 seconds to heat up to max
    public static float coolingSpeed = heatingSpeed / 4F;
    public static int outputFluidProductionRate = 3; // mB per tick; 3 * 20 = 60 mB per second

    public TileEntityDieselProducer()
    {
        super(ReferenceNames.NAME_TILE_DIESEL_PRODUCER, FluidRegistry.getFluid(ReferenceNames.NAME_FLUID_OIL), FluidRegistry.getFluid(ReferenceNames.NAME_FLUID_DIESEL));
        this.itemStacks = new ItemStack[5];
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (this.worldObj.isRemote == true) {
            return;
        }

        if (this.worldObj.isRemote == false) {
            this.runProcess(Configs.dieselProducerEnergyPerDieselBucket, Configs.dieselProducerOilPerDieselBucket, outputFluidProductionRate);
        }
    }

    /**
     * Converts input fluid into output fluid, if there is enough energy available,
     * enough space in the output tank, and input fluid in the input tank.
     */
    public boolean runProcess(int energyPerBucket, int inputFluidPerOutput, int productionRate)
    {

        if (this.temperature < MAX_TEMPERATURE && this.energyStorage.getEnergyStored() >= heatingEnergyperTick)
        {
            this.energyStorage.extractEnergy(heatingEnergyperTick, false);
            this.temperature += Math.min(MAX_TEMPERATURE - this.temperature, heatingSpeed);
        }
        else if (this.energyStorage.getEnergyStored() >= heatMaintainEnergyperTick)
        {
            this.energyStorage.extractEnergy(heatMaintainEnergyperTick, false);
        }
        else if (this.temperature > 20)
        {
            this.temperature -= Math.min(this.temperature, coolingSpeed);
        }

        if (this.fluidOutput == null || this.temperature < REQUIRED_TEMPERATURE)
        {
            return false;
        }

        int energyRequired = energyPerBucket * productionRate / 1000;
        int inputFluidRequired = inputFluidPerOutput * productionRate / 1000;

        // Handle the remaining bits in the tank that are below the regular production rate
        if (this.energyStorage.getEnergyStored() < energyRequired || this.tankInput.getFluidAmount() < inputFluidRequired)
        {
            productionRate = 1;
            energyRequired = energyPerBucket / 1000;
            inputFluidRequired = inputFluidPerOutput / 1000;
        }

        if (this.energyStorage.getEnergyStored() >= energyRequired && this.tankInput.getFluidAmount() >= inputFluidRequired)
        {
            FluidStack fluidStack = new FluidStack(this.fluidOutput, productionRate);
            // Enough room to store the produced fluid amount
            if (this.tankOutput.fill(fluidStack, false) == productionRate)
            {
                this.energyStorage.extractEnergy(energyRequired, false);
                this.tankInput.drain(inputFluidRequired, true);
                this.tankOutput.fill(fluidStack, true);
                return true;
            }
        }

        return false;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side)
    {
        if (side == 0)
        {
            return SLOTS_FUEL;
        }

        return SLOTS_NOT_FUEL;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public GuiFuelCraftInventory getGui(InventoryPlayer inventoryPlayer)
    {
        return new GuiDieselProducer(this.getContainer(inventoryPlayer), this);
    }
}
