package iceman11a.fuelcraft.inventory;

import iceman11a.fuelcraft.entity.EntityDieselTrainEngine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class ContainerDieselEngine extends Container
{
    public ContainerDieselEngine(InventoryPlayer inventoryPlayer, EntityDieselTrainEngine entity)
    {
        super();
        //this.addPlayerInventorySlots(8, 94);
    }

    protected void addCustomInventorySlots()
    {
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int var, int val)
    {
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return false;
    }
}
