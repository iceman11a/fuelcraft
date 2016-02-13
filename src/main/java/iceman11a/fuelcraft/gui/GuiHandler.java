package iceman11a.fuelcraft.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import iceman11a.fuelcraft.reference.ReferenceGuiIds;
import iceman11a.fuelcraft.tileentity.TileEntityFuelCraftInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;


public class GuiHandler implements IGuiHandler
{

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        if (player == null || world == null)
        {
            return null;
        }

        switch (id)
        {
            case ReferenceGuiIds.GUI_ID_TILE_ENTITY_GENERIC:
                TileEntity te = world.getTileEntity(x, y, z);
                if (te != null && te instanceof TileEntityFuelCraftInventory)
                {
                    return ((TileEntityFuelCraftInventory)te).getContainer(player.inventory);
                }
                break;

            default:
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        if (player == null || world == null)
        {
            return null;
        }

        switch (id)
        {
            case ReferenceGuiIds.GUI_ID_TILE_ENTITY_GENERIC:
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
