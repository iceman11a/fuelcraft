package iceman11a.fuelcraft.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import iceman11a.fuelcraft.gui.*;
import iceman11a.fuelcraft.tileentity.TileEntityFuelCraftInventory;
import iceman11a.fuelcraft.machines.ReferenceGuiIds;



public class GuiHandler implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		System.out.println("Hello world");

		 if (player == null || world == null)
	        {
	            return null;
	        }

	        switch (id)
	        {
	            case ReferenceGuiIds.GUI_ID_TILE_ENTITY_GENERIC:
	            	System.out.println("Hello world");
	                TileEntity te = world.getTileEntity(x, y, z);
	                if (te != null && te instanceof TileEntityFuelCraftInventory)
	                {
	                    return ((TileEntityFuelCraftInventory)te). getContainer(player.inventory);	                    
	                }	                
	                break;
	                
	            default:	            	
	        }

	        return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		System.out.println("Hello world");
		System.out.println(ReferenceGuiIds.GUI_ID_TILE_ENTITY_GENERIC);
		 if (player == null || world == null)
	        {
	            return null;
	        }

		 switch (id)
	        {
	            case ReferenceGuiIds.GUI_ID_TILE_ENTITY_GENERIC:
	            	System.out.println("Hello world");
	                TileEntity te = world.getTileEntity(x, y, z);
	                if (te != null && te instanceof TileEntityFuelCraftInventory)
	                {
	                    return ((TileEntityFuelCraftInventory)te).getGui(player.inventory);
	                }	                
	                break;
	                
	            default:
	            	
	        }	       

	        return null;
	}	
}
