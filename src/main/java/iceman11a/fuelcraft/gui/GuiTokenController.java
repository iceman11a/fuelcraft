package iceman11a.fuelcraft.gui;

import iceman11a.fuelcraft.inventory.ContainerTileEntityInventory;
import iceman11a.fuelcraft.tileentity.TileEntityTokenController;

public class GuiTokenController extends GuiFuelCraftInventory
{
    public GuiTokenController(ContainerTileEntityInventory container, TileEntityTokenController te)
    {
        super(container, te);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float gameTicks, int mouseX, int mouseY)
    {
        super.drawGuiContainerBackgroundLayer(gameTicks, mouseX, mouseY);
    }
}
