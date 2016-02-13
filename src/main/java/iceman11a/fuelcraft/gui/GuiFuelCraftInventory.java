package iceman11a.fuelcraft.gui;

import iceman11a.fuelcraft.inventory.ContainerTileEntityInventory;
import iceman11a.fuelcraft.reference.ReferenceReflection;
import iceman11a.fuelcraft.reference.ReferenceTextures;
import iceman11a.fuelcraft.tileentity.TileEntityFuelCraftInventory;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.Slot;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

public class GuiFuelCraftInventory extends GuiContainer
{

    protected TileEntityFuelCraftInventory te;
    protected ResourceLocation guiTexture;

    public GuiFuelCraftInventory(ContainerTileEntityInventory container, TileEntityFuelCraftInventory te)
    {
        super(container);
        this.te = te;
        this.guiTexture = ReferenceTextures.getGuiTexture("gui.container." + te.getTEName());
        this.ySize = 174;
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

    /**
    * Used to draw tooltips when hovering the mouse over some slots.
    */
    protected void drawTooltips(int mouseX, int mouseY)
    {
        Slot slot = null;

        try
        {
            slot = (Slot)ReferenceReflection.fieldGuiContainer_theSlot.get(this);
        }
        catch (IllegalAccessException e)
        {
            return;
        }

        if (slot != null && slot.getHasStack() == false)
        {
            List<String> list = new ArrayList<String>();
            this.addSlotHoveringText(slot, list);

            this.drawHoveringText(list, mouseX, mouseY, this.fontRendererObj);
        }
    }

    protected void addSlotHoveringText(Slot slot, List<String> list) { }

    protected void bindTexture(ResourceLocation rl)
    {
        this.mc.renderEngine.bindTexture(rl);
    }

    /*
     * This method has been stolen from BuildCraft
    */
    public void drawFluid(FluidStack fluid, int x, int y, int width, int height, int maxCapacity)
    {
        if (fluid == null || fluid.getFluid() == null)
        {
            return;
        }

        IIcon icon = fluid.getFluid().getIcon(fluid);
        if (icon == null)
        {
            icon = ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
        }

        mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        setGLColorFromInt(fluid.getFluid().getColor(fluid));
        int fullX = width / 16;
        int fullY = height / 16;
        int lastX = width - fullX * 16;
        int lastY = height - fullY * 16;
        int level = fluid.amount * height / maxCapacity;
        int fullLvl = (height - level) / 16;
        int lastLvl = (height - level) - fullLvl * 16;
        for (int i = 0; i < fullX; i++)
        {
            for (int j = 0; j < fullY; j++)
            {
                if (j >= fullLvl)
                {
                    drawCutIcon(icon, x + i * 16, y + j * 16, 16, 16, j == fullLvl ? lastLvl : 0);
                }
            }
        }
        for (int i = 0; i < fullX; i++)
        {
            drawCutIcon(icon, x + i * 16, y + fullY * 16, 16, lastY, fullLvl == fullY ? lastLvl : 0);
        }
        for (int i = 0; i < fullY; i++)
        {
            if (i >= fullLvl)
            {
                drawCutIcon(icon, x + fullX * 16, y + i * 16, lastX, 16, i == fullLvl ? lastLvl : 0);
            }
        }
        drawCutIcon(icon, x + fullX * 16, y + fullY * 16, lastX, lastY, fullLvl == fullY ? lastLvl : 0);
    }

    /*
     * This method has been stolen from BuildCraft
    */
    private void drawCutIcon(IIcon icon, int x, int y, int width, int height, int cut)
    {
        Tessellator tess = Tessellator.instance;
        tess.startDrawingQuads();
        tess.addVertexWithUV(x, y + height, zLevel, icon.getMinU(), icon.getInterpolatedV(height));
        tess.addVertexWithUV(x + width, y + height, zLevel, icon.getInterpolatedU(width), icon.getInterpolatedV(height));
        tess.addVertexWithUV(x + width, y + cut, zLevel, icon.getInterpolatedU(width), icon.getInterpolatedV(cut));
        tess.addVertexWithUV(x, y + cut, zLevel, icon.getMinU(), icon.getInterpolatedV(cut));
        tess.draw();
    }

    /*
     * This method has been stolen from BuildCraft
    */
    public static void setGLColorFromInt(int color)
    {
        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;
        GL11.glColor4f(red, green, blue, 1.0F);
    }
}
