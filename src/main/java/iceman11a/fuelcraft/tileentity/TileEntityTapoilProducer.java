package iceman11a.fuelcraft.tileentity;

import iceman11a.fuelcraft.config.Configs;
import iceman11a.fuelcraft.gui.GuiFuelCraftInventory;
import iceman11a.fuelcraft.gui.GuiTapoilProducer;
import iceman11a.fuelcraft.inventory.ContainerTapoilProducer;
import iceman11a.fuelcraft.reference.ReferenceNames;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityTapoilProducer extends TileEntityFluidProcessor
{
    public static final int SLOT_SAPLING = 5;
    public static final int SLOT_COAL = 6;

    public static final int[] SLOTS_NOT_FUEL = new int[] {1, 2, 3, 4, 5, 6};

    public static final int MAX_TEMPERATURE = 200;
    public static final int REQUIRED_TEMPERATURE = 175;

    public static int heatingEnergyperTick = 20;
    public static int heatMaintainEnergyperTick = 5;
    public static float heatingSpeed = MAX_TEMPERATURE / (120F * 20F); // 120 seconds to heat up to max
    public static float coolingSpeed = heatingSpeed / 4F;
    public static int outputFluidProductionRate = 3; // mB per tick; 3 * 20 = 60 mB per second

    protected int saplingAmount;
    protected int coalAmount;

    public TileEntityTapoilProducer()
    {
        super(ReferenceNames.NAME_TILE_TAPOIL_PRODUCER, FluidRegistry.getFluid("water"), FluidRegistry.getFluid(ReferenceNames.NAME_FLUID_TAPOIL));
        this.itemStacks = new ItemStack[7];
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        this.saplingAmount = nbt.getInteger("SaplingAmount");
        this.coalAmount = nbt.getInteger("CoalAmount");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        nbt.setInteger("SaplingAmount", this.saplingAmount);
        nbt.setInteger("CoalAmount", this.coalAmount);
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (this.worldObj.isRemote == true)
        {
            return;
        }

        this.runProcess(Configs.tapoilProducerEnergyPerTapoilBucket,
                        Configs.tapoilProducerWaterPerTapoilBucket,
                        Configs.tapoilProducerSaplingValue,
                        Configs.tapoilProducerCoalValue,
                        outputFluidProductionRate);
    }

    /**
     * Converts input fluid into output fluid, if there are enough input resources available.
     */
    public boolean runProcess(int energyPerBucket, int inputFluidPerOutput, int saplingValue, int coalValue, int productionRate)
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

        if (this.saplingAmount < productionRate)
        {
            if (this.getStackInSlot(SLOT_SAPLING) != null)
            {
                this.decrStackSize(SLOT_SAPLING, 1);
                this.saplingAmount += Configs.tapoilProducerSaplingValue;
            }
        }

        if (this.coalAmount < productionRate)
        {
            if (this.getStackInSlot(SLOT_COAL) != null)
            {
                this.decrStackSize(SLOT_COAL, 1);
                this.coalAmount += Configs.tapoilProducerCoalValue;
            }
        }

        float energyRequired = (float)energyPerBucket * productionRate / 1000;
        float inputFluidRequired = (float)inputFluidPerOutput * productionRate / 1000;

        // Handle the remaining bits in the tank that are below the regular production rate
        if (this.energyStorage.getEnergyStored() < energyRequired ||
            this.tankInput.getFluidAmount() < inputFluidRequired ||
            this.saplingAmount < productionRate ||
            this.coalAmount < productionRate)
        {
            productionRate = 1;
            energyRequired = energyPerBucket / 1000;
            inputFluidRequired = inputFluidPerOutput / 1000;
        }

        if (this.energyStorage.getEnergyStored() >= energyRequired &&
            this.tankInput.getFluidAmount() >= inputFluidRequired &&
            this.saplingAmount >= productionRate &&
            this.coalAmount >= productionRate)
        {
            FluidStack fluidStack = new FluidStack(this.fluidOutput, productionRate);
            // Enough room to store the produced fluid amount
            if (this.tankOutput.fill(fluidStack, false) == productionRate)
            {
                this.energyStorage.extractEnergy(MathHelper.ceiling_float_int(energyRequired), false);
                this.tankInput.drain(MathHelper.ceiling_float_int(inputFluidRequired), true);
                this.saplingAmount -= productionRate;
                this.coalAmount -= productionRate;
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

        if (slotNum == SLOT_SAPLING)
        {
            for (int id : OreDictionary.getOreIDs(stack))
            {
                if (OreDictionary.getOreName(id).equals("treeSapling"))
                {
                    return true;
                }
            }
        }

        if (slotNum == SLOT_COAL)
        {
            return stack != null && stack.getItem() == Items.coal;
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

    @Override
    public ContainerTapoilProducer getContainer(InventoryPlayer inventoryPlayer)
    {
        return new ContainerTapoilProducer(inventoryPlayer, this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public GuiFuelCraftInventory getGui(InventoryPlayer inventoryPlayer)
    {
        return new GuiTapoilProducer(this.getContainer(inventoryPlayer), this);
    }
}
