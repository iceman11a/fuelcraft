package iceman11a.fuelcraft.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityDieselTrainEngine extends Entity
{
    public boolean canMove = false; // Can the diesel engine move

    public int engineTemperature = 0;
    public int engineSpeed = 0; // the current speed of the engine (MPH = Miles per Hour)

    public EntityDieselTrainEngine(World world)
    {
        super(world);
    }

    /*
    @Override
    public ContainerDieselEngine getContainer(InventoryPlayer inventoryPlayer)
    {
        return new ContainerDieselEngine(inventoryPlayer, null);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public GuiFuelCraftInventory getGui(InventoryPlayer inventoryPlayer)
    {
        return new GuiDieselEngine(this.getContainer(inventoryPlayer), this);
    }
    */

    @Override
    protected void entityInit()
    {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt)
    {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt)
    {
    }
}
