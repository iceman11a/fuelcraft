package iceman11a.fuelcraft.tileentity;

import iceman11a.fuelcraft.gui.GuiFuelCraftInventory;
import iceman11a.fuelcraft.gui.GuiTokenController;
import iceman11a.fuelcraft.inventory.ContainerTokenController;
import iceman11a.fuelcraft.reference.ReferenceNames;
import net.minecraft.entity.player.InventoryPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityTokenController extends TileEntityFuelCraftInventory
{
    public TileEntityTokenController()
    {
        super(ReferenceNames.NAME_TILE_TOKEN_CONTROLLER);
    }

    @Override
    public ContainerTokenController getContainer(InventoryPlayer inventoryPlayer)
    {
        return new ContainerTokenController(inventoryPlayer, this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public GuiFuelCraftInventory getGui(InventoryPlayer inventoryPlayer)
    {
        return new GuiTokenController(this.getContainer(inventoryPlayer), this);
    }
}
