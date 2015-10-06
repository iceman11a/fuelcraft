package iceman11a.fuelcraft.gui;

	
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import iceman11a.fuelcraft.inventory.ContainerTileEntityInventory;
import iceman11a.fuelcraft.tileentity.TileEntityDieselProducer;
import iceman11a.fuelcraft.tileentity.TileEntityFuelCraftInventory;
import iceman11a.fuelcraft.machines.ReferenceTextures;

public class GuiDieselProducer extends GuiFuelCraftInventory
{
    
	protected TileEntityFuelCraftInventory te;
    protected ResourceLocation guiTexture;

    public GuiDieselProducer(ContainerTileEntityInventory container, TileEntityDieselProducer te)
    {
        super(container, te);
        this.te = te;        
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float gameTicks, int mouseX, int mouseY)
    {    	
    	super.drawGuiContainerBackgroundLayer(gameTicks, mouseX, mouseY);    	
    } 

}


