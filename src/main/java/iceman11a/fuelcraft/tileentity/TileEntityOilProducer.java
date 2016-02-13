package iceman11a.fuelcraft.tileentity;

import iceman11a.fuelcraft.config.Configs;
import iceman11a.fuelcraft.gui.GuiFuelCraftInventory;
import iceman11a.fuelcraft.gui.GuiOilProducer;
import iceman11a.fuelcraft.inventory.ContainerOilProducer;
import iceman11a.fuelcraft.reference.ReferenceNames;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityOilProducer extends TileEntityFluidProcessor
{
    public static final int SLOT_BLAZEPOWDER = 5;

    public static final int[] SLOTS_NOT_FUEL = new int[] {1, 2, 3, 4, 5};

    public static final int MAX_TEMPERATURE = 650;
    public static final int REQUIRED_TEMPERATURE = 600;

    public static int heatingEnergyperTick = 20;
    public static int heatMaintainEnergyperTick = 5;
    public static float heatingSpeed = MAX_TEMPERATURE / (180F * 20F); // 180 seconds to heat up to max
    public static float coolingSpeed = heatingSpeed / 4F;
    public static int outputFluidProductionRate = 3; // mB per tick; 3 * 20 = 60 mB per second

    protected int blazepowderAmount;

    public TileEntityOilProducer()
    {
        super(ReferenceNames.NAME_TILE_OIL_PRODUCER, FluidRegistry.getFluid(ReferenceNames.NAME_FLUID_TAPOIL), FluidRegistry.getFluid(ReferenceNames.NAME_FLUID_OIL));
        this.itemStacks = new ItemStack[6];
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        this.blazepowderAmount = nbt.getInteger("BlazePowderAmount");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        nbt.setInteger("BlazePowderAmount", this.blazepowderAmount);
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (this.worldObj.isRemote == true)
        {
            return;
        }

        this.runProcess(Configs.oilProducerEnergyPerOilBucket,
                        Configs.oilProducerTapoilPerOilBucket,
                        Configs.oilProducerBlazepowderValue,
                        outputFluidProductionRate);
    }

    /**
     * Converts input fluid into output fluid, if there are enough input resources available.
     */
    public boolean runProcess(int energyPerBucket, int inputFluidPerOutput, int blazePowderValue, int productionRate)
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

        if (this.blazepowderAmount < productionRate)
        {
            if (this.getStackInSlot(SLOT_BLAZEPOWDER) != null)
            {
                this.decrStackSize(SLOT_BLAZEPOWDER, 1);
                this.blazepowderAmount += Configs.oilProducerBlazepowderValue;
            }
        }

        float energyRequired = (float)energyPerBucket * productionRate / 1000;
        float inputFluidRequired = (float)inputFluidPerOutput * productionRate / 1000;

        // Handle the remaining bits in the tank that are below the regular production rate
        if (this.energyStorage.getEnergyStored() < energyRequired ||
            this.tankInput.getFluidAmount() < inputFluidRequired ||
            this.blazepowderAmount < productionRate)
        {
            productionRate = 1;
            energyRequired = energyPerBucket / 1000;
            inputFluidRequired = inputFluidPerOutput / 1000;
        }

        if (this.energyStorage.getEnergyStored() >= energyRequired &&
            this.tankInput.getFluidAmount() >= inputFluidRequired &&
            this.blazepowderAmount >= productionRate)
        {
            FluidStack fluidStack = new FluidStack(this.fluidOutput, productionRate);
            // Enough room to store the produced fluid amount
            if (this.tankOutput.fill(fluidStack, false) == productionRate) {
                this.energyStorage.extractEnergy(MathHelper.ceiling_float_int(energyRequired), false);
                this.tankInput.drain(MathHelper.ceiling_float_int(inputFluidRequired), true);
                this.blazepowderAmount -= productionRate;
                this.tankOutput.fill(fluidStack, true);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isItemValidForSlot(int slotNum, ItemStack stack)
    {
        if (super.isItemValidForSlot(slotNum, stack) == true)
        {
            return true;
        }

        if (slotNum == SLOT_BLAZEPOWDER)
        {
            return stack != null && stack.getItem() == Items.blaze_powder;
        }

        return false;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side)
    {
        return side == 0 ? SLOTS_FUEL : SLOTS_NOT_FUEL;
    }

    @Override
    public ContainerOilProducer getContainer(InventoryPlayer inventoryPlayer)
    {
        return new ContainerOilProducer(inventoryPlayer, this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public GuiFuelCraftInventory getGui(InventoryPlayer inventoryPlayer)
    {
        return new GuiOilProducer(this.getContainer(inventoryPlayer), this);
    }
}
