package iceman11a.fuelcraft.fluid;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

public class FluidTankFuelcraft implements IFluidTank
{
    protected TileEntity tileEntity;
    protected FluidStack fluidStack;
    protected int capacity;

    public FluidTankFuelcraft(int capacity)
    {
        this(null, null, capacity);
    }

    public FluidTankFuelcraft(TileEntity tileEntity, FluidStack fluidStack, int capacity)
    {
        this.tileEntity = tileEntity;
        this.fluidStack = fluidStack;
        this.capacity = capacity;
    }

    public void setFluid(FluidStack fluidStack)
    {
        this.fluidStack = fluidStack;
    }

    @Override
    public FluidStack getFluid()
    {
        return this.fluidStack;
    }

    @Override
    public int getFluidAmount()
    {
        if (this.fluidStack != null)
        {
            return this.fluidStack.amount;
        }

        return 0;
    }

    @Override
    public int getCapacity()
    {
        return this.capacity;
    }

    @Override
    public FluidTankInfo getInfo()
    {
        return new FluidTankInfo(this);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill)
    {
        if (resource == null)
        {
            return 0;
        }

        if (!doFill)
        {
            if (this.fluidStack == null)
            {
                return Math.min(this.capacity, resource.amount);
            }

            if (this.fluidStack.isFluidEqual(resource) == false)
            {
                return 0;
            }

            return Math.min(this.capacity - this.fluidStack.amount, resource.amount);
        }

        if (this.fluidStack == null)
        {
            this.fluidStack = new FluidStack(resource, Math.min(this.capacity, resource.amount));

            if (this.tileEntity != null)
            {
                FluidEvent.fireEvent(new FluidEvent.FluidFillingEvent(this.fluidStack, this.tileEntity.getWorldObj(), this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, this, this.fluidStack.amount));
            }

            return this.fluidStack.amount;
        }

        if (this.fluidStack.isFluidEqual(resource) == false)
        {
            return 0;
        }

        int filled = this.capacity - this.fluidStack.amount;
        if (resource.amount < filled)
        {
            this.fluidStack.amount += resource.amount;
            filled = resource.amount;
        }
        else
        {
            this.fluidStack.amount = this.capacity;
        }

        if (this.tileEntity != null)
        {
            FluidEvent.fireEvent(new FluidEvent.FluidFillingEvent(this.fluidStack, this.tileEntity.getWorldObj(), this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, this, filled));
        }

        return filled;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain)
    {
        if (this.fluidStack == null)
        {
            return null;
        }

        int drained = maxDrain;
        if (this.fluidStack.amount < drained)
        {
            drained = this.fluidStack.amount;
        }

        FluidStack stack = new FluidStack(this.fluidStack, drained);
        if (doDrain)
        {
            this.fluidStack.amount -= drained;
            if (this.fluidStack.amount <= 0)
            {
                this.fluidStack = null;
            }

            if (this.tileEntity != null)
            {
                FluidEvent.fireEvent(new FluidEvent.FluidDrainingEvent(this.fluidStack, this.tileEntity.getWorldObj(), this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, this, drained));
            }
        }

        return stack;
    }
}
