package iceman11a.fuelcraft.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceman11a.fuelcraft.gui.GuiDieselProducer;
import iceman11a.fuelcraft.gui.GuiFuelCraftInventory;
import iceman11a.fuelcraft.inventory.ContainerDieselProducer;
import iceman11a.fuelcraft.machines.ReferenceNames;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;


//Line 64

public class TileEntityDieselProducer extends TileEntityFuelCraftInventory
{
    protected String tileEntityName;
    
    public TileEntityDieselProducer()
    {
    	super(ReferenceNames.NAME_TILE_ENTITY_DIESEL_PRODUCER);
        this.itemStacks = new ItemStack[5];
        
    }
    
    /*
    @Override
    public ContainerEnderInfuser getContainer(InventoryPlayer inventoryPlayer)
    {
        return new ContainerEnderInfuser(inventoryPlayer, this);
    }
    */
        
    @Override
    public ContainerDieselProducer getContainer(InventoryPlayer inventoryPlayer)
    {
        return new ContainerDieselProducer(inventoryPlayer, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiFuelCraftInventory getGui(InventoryPlayer inventoryPlayer)
    {
        return new GuiDieselProducer(this.getContainer(inventoryPlayer), this);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        //this.rotation = nbt.getByte("Rotation");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        //nbt.setByte("Rotation", (byte)this.rotation);
    }
      
    /**
     * Check if the given item works as a fuel source in this furnace
     * @param stack
     * @return
     */
    public static boolean isItemFuel(ItemStack stack)
    {
        return false; // itemContainsFluidFuel(stack) || getItemBurnTime(stack) > 0;
    }
    
}