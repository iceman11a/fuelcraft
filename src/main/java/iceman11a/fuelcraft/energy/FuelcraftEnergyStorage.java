package iceman11a.fuelcraft.energy;

import cofh.api.energy.IEnergyStorage;

public class FuelcraftEnergyStorage implements IEnergyStorage
{
    private int capacity;
    private int energy;

    public FuelcraftEnergyStorage(int capacity)
    {
        this.capacity = capacity;
    }

    /**
     * Sets the value of stored energy to newValue.
     * @param newValue
     * @return true on success, false if newValue is out of the allowed range
     */
    public boolean setStoredEnergy(int newValue)
    {
        if (newValue >= 0 && newValue <= this.capacity)
        {
            this.energy = newValue;
            return true;
        }

        return false;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        int amount = Math.min(maxReceive, this.capacity - this.energy);

        if (simulate == false)
        {
            this.energy += amount;

            if (this.energy > this.capacity)
            {
                this.energy = this.capacity;
            }
        }

        return amount;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        int amount = Math.min(maxExtract, this.energy);

        if (simulate == false)
        {
            this.energy -= amount;

            if (this.energy < 0)
            {
                this.energy = 0;
            }
        }

        return amount;
    }

    @Override
    public int getEnergyStored()
    {
        return this.energy;
    }

    @Override
    public int getMaxEnergyStored()
    {
        return this.capacity;
    }
}
