package iceman11a.fuelcraft.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import iceman11a.fuelcraft.inventory.ContainerTileEntityInventory;
import iceman11a.fuelcraft.machines.ReferenceTextures;
import iceman11a.fuelcraft.tileentity.TileEntityFuelCraftInventory;

public class GuiFuelCraftInventory extends GuiContainer
{
    protected TileEntityFuelCraftInventory te;
    protected ResourceLocation guiTexture;

    public GuiFuelCraftInventory(ContainerTileEntityInventory container, TileEntityFuelCraftInventory te)
    {
        super(container);
        this.te = te;
        this.guiTexture = ReferenceTextures.getGuiTexture("gui." + te.getTEName());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float gameTicks)
    {
        super.drawScreen(mouseX, mouseY, gameTicks);
        this.drawTooltips(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float gameTicks, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.bindTexture(this.guiTexture);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }

    protected void drawTooltips(int mouseX, int mouseY)
    {
    }

    protected void bindTexture(ResourceLocation rl)
    {
        this.mc.renderEngine.bindTexture(rl);
    }
}
