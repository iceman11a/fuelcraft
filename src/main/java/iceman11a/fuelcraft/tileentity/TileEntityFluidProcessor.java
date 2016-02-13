package iceman11a.fuelcraft.tileentity;

import iceman11a.fuelcraft.energy.FuelcraftEnergyStorage;
import iceman11a.fuelcraft.fluid.FluidTankFuelcraft;
import iceman11a.fuelcraft.inventory.ContainerFluidProcessor;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cofh.api.energy.IEnergyReceiver;

public abstract class TileEntityFluidProcessor extends TileEntityFuelCraftSidedInventory implements IFluidHandler, IEnergyReceiver
{
    public static final int SLOT_FUEL                      = 0;
    public static final int SLOT_INPUT_FLUID_BUCKET_IN     = 1;
    public static final int SLOT_INPUT_FLUID_BUCKET_OUT    = 2;
    public static final int SLOT_OUTPUT_FLUID_BUCKET_IN    = 3;
    public static final int SLOT_OUTPUT_FLUID_BUCKET_OUT   = 4;

    public static final int[] SLOTS_FUEL = new int[] {0};

    public static int capacityFluidInput    =  16000;
    public static int capacityFluidOutput   =  16000;
    public static int capacityEnergy        = 100000;
    public static int energyPerFuelTick     = 30; // 30 should be in line with ThermalExpansion's default energy value per vanilla item burn time

    protected Fluid fluidInput;
    protected Fluid fluidOutput;

    protected FluidTankFuelcraft tankInput;
    protected FluidTankFuelcraft tankOutput;

    protected FuelcraftEnergyStorage energyStorage;

    protected int counter;
    protected int fuelBurnTime;
    protected int fuelBurnTimeFresh;
    protected float temperature = 20F;

    public TileEntityFluidProcessor(String name, Fluid inputFluid, Fluid outputFluid)
    {
        super(name);
        this.fluidInput = inputFluid;
        this.fluidOutput = outputFluid;
        this.tankInput = new FluidTankFuelcraft(this, null, capacityFluidInput);
        this.tankOutput = new FluidTankFuelcraft(this, null, capacityFluidOutput);
        this.energyStorage = new FuelcraftEnergyStorage(capacityEnergy);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        this.energyStorage.setStoredEnergy(nbt.getInteger("StoredEnergy"));
        this.fuelBurnTime = nbt.getInteger("BurnTime");
        this.fuelBurnTimeFresh = nbt.getInteger("BurnTimeTotal");
        this.temperature = nbt.getFloat("Temperature");

        if (nbt.hasKey("FluidInput", Constants.NBT.TAG_COMPOUND) == true)
        {
            this.tankInput.setFluid(FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("FluidInput")));
        }

        if (nbt.hasKey("FluidOutput", Constants.NBT.TAG_COMPOUND) == true)
        {
            this.tankOutput.setFluid(FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("FluidOutput")));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        nbt.setInteger("StoredEnergy", this.energyStorage.getEnergyStored());
        nbt.setInteger("BurnTime", this.fuelBurnTime);
        nbt.setInteger("BurnTimeTotal", this.fuelBurnTimeFresh);
        nbt.setFloat("Temperature", this.temperature);

        if (this.tankInput.getFluid() != null)
        {
            nbt.setTag("FluidInput", this.tankInput.getFluid().writeToNBT(new NBTTagCompound()));
        }

        if (this.tankOutput.getFluid() != null)
        {
            nbt.setTag("FluidOutput", this.tankOutput.getFluid().writeToNBT(new NBTTagCompound()));
        }
    }

    @Override
    public void updateEntity()
    {
        if (this.worldObj.isRemote == true)
        {
            return;
        }

        if (++counter >= 5)
        {
            counter = 0;

            //if (this.tankInput.getFluidAmount() == 16000) this.tankInput.drain(1000, true); // FIXME debug
            //if (this.tankOutput.getFluidAmount() == 0) this.tankOutput.fill(new FluidStack(this.fluidOutput, 16000), true); // FIXME debug

            this.consumeInputFluidItem();
            this.fillOutputFluidItem();
        }

        this.burnFuelItem();
    }

    public float getTemperature()
    {
        return this.temperature;
    }

    public int getFuelBurnTime()
    {
        return this.fuelBurnTime;
    }

    public int getFuelBurnTimeFresh()
    {
        return this.fuelBurnTimeFresh;
    }

    /**
     * Burns fuel from the input slot, if the energy buffer is not full.
     */
    public void burnFuelItem()
    {
        if (this.fuelBurnTime == 0 && this.energyStorage.getEnergyStored() < this.energyStorage.getMaxEnergyStored() && this.itemStacks[SLOT_FUEL] != null)
        {
            this.fuelBurnTime = TileEntityFurnace.getItemBurnTime(this.itemStacks[SLOT_FUEL]);
            this.fuelBurnTimeFresh = this.fuelBurnTime;

            if (this.fuelBurnTime > 0)
            {
                this.decrStackSize(SLOT_FUEL, 1);
            }
        }

        if (this.fuelBurnTime > 0)
        {
            this.fuelBurnTime--;

            if (this.energyStorage.getEnergyStored() < this.energyStorage.getMaxEnergyStored())
            {
                this.energyStorage.receiveEnergy(energyPerFuelTick, false);
            }
        }
    }

    /**
     * Tries to take one fluid container item from the input slot and add the fluid from it to the input tank
     * an then return the empty container item (if any) from it to the output slot.
     */
    public boolean consumeInputFluidItem()
    {
        // Has input items, and output slot is empty or has space in the output slot
        if (this.itemStacks[SLOT_INPUT_FLUID_BUCKET_IN] != null)
        {
            if (this.itemHasValidInputFluid(this.itemStacks[SLOT_INPUT_FLUID_BUCKET_IN]) == false)
            {
                return false;
            }

            ItemStack stackIn = this.itemStacks[SLOT_INPUT_FLUID_BUCKET_IN].copy();
            stackIn.stackSize = 1;
            FluidStack fluidStack = FluidContainerRegistry.getFluidForFilledItem(stackIn);
            int amount = fluidStack != null ? fluidStack.amount : 0;
            ItemStack stackOut = FluidContainerRegistry.drainFluidContainer(stackIn);

            if (fluidStack == null)
            {
                return false;
            }

            // Can add the fluid from one input container to the input tank
            if (this.tankInput.fill(fluidStack, false) == amount)
            {
                // Has an empty container item as output
                if (stackOut != null)
                {
                    if (this.itemStacks[SLOT_INPUT_FLUID_BUCKET_OUT] == null)
                    {
                        this.tankInput.fill(fluidStack, true);
                        this.decrStackSize(SLOT_INPUT_FLUID_BUCKET_IN, 1);
                        this.itemStacks[SLOT_INPUT_FLUID_BUCKET_OUT] = stackOut;
                        return true;
                    }
                    else if (this.itemStacks[SLOT_INPUT_FLUID_BUCKET_OUT].stackSize < this.itemStacks[SLOT_INPUT_FLUID_BUCKET_OUT].getMaxStackSize() &&
                             stackOut.isItemEqual(this.itemStacks[SLOT_INPUT_FLUID_BUCKET_OUT]))
                    {
                        this.tankInput.fill(fluidStack, true);
                        this.decrStackSize(SLOT_INPUT_FLUID_BUCKET_IN, 1);
                        this.itemStacks[SLOT_INPUT_FLUID_BUCKET_OUT].stackSize++;
                        return true;
                    }
                }
                // No empty container item as a result of emptying a fluid container, just transfer the fluid
                else
                {
                    this.tankInput.fill(fluidStack, true);
                    this.decrStackSize(SLOT_INPUT_FLUID_BUCKET_IN, 1);
                    return true;
                }
            }
        }

        return false;
    }

    public boolean fillOutputFluidItem()
    {
        if (this.fluidOutput == null)
        {
            return false;
        }

        // Has input items, and output slot is empty or has space in the output slot
        if (this.itemStacks[SLOT_OUTPUT_FLUID_BUCKET_IN] != null)
        {
            if (this.itemIsValidContainerForOutputFluid(this.itemStacks[SLOT_OUTPUT_FLUID_BUCKET_IN]) == false)
            {
                return false;
            }

            ItemStack stackIn = this.itemStacks[SLOT_OUTPUT_FLUID_BUCKET_IN].copy();
            stackIn.stackSize = 1;
            int capacity = FluidContainerRegistry.getContainerCapacity(new FluidStack(this.fluidOutput, 0), stackIn);
            FluidStack fluidStack = this.tankOutput.drain(capacity, false);
            if (fluidStack == null || fluidStack.amount < capacity)
            {
                return false;
            }

            ItemStack stackOut = FluidContainerRegistry.fillFluidContainer(fluidStack, stackIn);
            if (stackOut == null)
            {
                return false;
            }

            if (this.itemStacks[SLOT_OUTPUT_FLUID_BUCKET_OUT] == null)
            {
                this.tankOutput.drain(capacity, true);
                this.itemStacks[SLOT_OUTPUT_FLUID_BUCKET_OUT] = stackOut;
                this.decrStackSize(SLOT_OUTPUT_FLUID_BUCKET_IN, 1);
                return true;
            }
            else if (this.itemStacks[SLOT_OUTPUT_FLUID_BUCKET_OUT].stackSize < this.itemStacks[SLOT_OUTPUT_FLUID_BUCKET_OUT].getMaxStackSize() &&
                     stackOut.isItemEqual(this.itemStacks[SLOT_OUTPUT_FLUID_BUCKET_OUT]))
            {
                this.tankOutput.drain(capacity, true);
                this.itemStacks[SLOT_OUTPUT_FLUID_BUCKET_OUT].stackSize++;
                this.decrStackSize(SLOT_OUTPUT_FLUID_BUCKET_IN, 1);
                return true;
            }
        }

        return false;
    }

    /**
     * Returns true, if the given ItemStack is a filled fluid container, which contains
     * a valid fluid for the input tank of this machine.
     */
    public boolean itemHasValidInputFluid(ItemStack stack)
    {
        return this.fluidInput != null && FluidContainerRegistry.isFilledContainer(stack) &&
                FluidContainerRegistry.containsFluid(stack, new FluidStack(this.fluidInput, FluidContainerRegistry.BUCKET_VOLUME));
    }

    public boolean itemIsValidContainerForOutputFluid(ItemStack stack)
    {
        return this.fluidOutput != null &&
                FluidContainerRegistry.fillFluidContainer(new FluidStack(this.fluidOutput, FluidContainerRegistry.BUCKET_VOLUME), stack) != null;
    }

    /**
     * Check if the given item works as a fuel source in this machine
     */
    public boolean isItemFuel(ItemStack stack)
    {
        return TileEntityFurnace.getItemBurnTime(stack) > 0;
    }

    @Override
    public boolean isItemValidForSlot(int slotNum, ItemStack stack)
    {
        if (slotNum == SLOT_INPUT_FLUID_BUCKET_OUT || slotNum == SLOT_OUTPUT_FLUID_BUCKET_OUT)
        {
            return false;
        }

        if (slotNum == SLOT_FUEL)
        {
            return this.isItemFuel(stack);
        }

        if (slotNum == SLOT_OUTPUT_FLUID_BUCKET_IN)
        {
            return this.itemIsValidContainerForOutputFluid(stack);
        }

        if (slotNum == SLOT_INPUT_FLUID_BUCKET_IN)
        {
            return this.itemHasValidInputFluid(stack);
        }

        return false;
    }

    @Override
    public boolean canExtractItem(int slotNum, ItemStack itemStack, int side)
    {
        return slotNum == SLOT_INPUT_FLUID_BUCKET_OUT || slotNum == SLOT_OUTPUT_FLUID_BUCKET_OUT;
    }

    public int getInputFluidAmount()
    {
        return this.tankInput.getFluidAmount();
    }

    public int getOutputFluidAmount()
    {
        return this.tankOutput.getFluidAmount();
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        // Only allow the input fluid into the input tank
        if (resource == null || resource.getFluid() != this.fluidInput)
        {
            return 0;
        }

        return this.tankInput.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        if (resource == null)
        {
            return null;
        }

        if (resource.isFluidEqual(this.tankInput.getFluid()) == true)
        {
            return this.tankInput.drain(resource.amount, doDrain);
        }

        if (resource.isFluidEqual(this.tankOutput.getFluid()))
        {
            return this.tankOutput.drain(resource.amount, doDrain);
        }

        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        return this.tankOutput.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        return this.fluidInput != null && this.fluidInput == fluid;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        //return this.fluidOutput != null && this.fluidOutput == fluid;
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        return new FluidTankInfo[] { this.tankInput.getInfo(), this.tankOutput.getInfo() };
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return true;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        return this.energyStorage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from)
    {
        return this.energyStorage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from)
    {
        return this.energyStorage.getMaxEnergyStored();
    }

    @Override
    public ContainerFluidProcessor getContainer(InventoryPlayer inventoryPlayer)
    {
        return new ContainerFluidProcessor(inventoryPlayer, this);
    }
}