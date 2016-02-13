package iceman11a.fuelcraft.client.renderer.item;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.IItemRenderer;

import iceman11a.fuelcraft.block.FuelcraftBlocks;
import iceman11a.fuelcraft.tileentity.TileEntityCrossingGate;

public class ItemRendererCrossingGate implements IItemRenderer
{
    private TileEntityCrossingGate te;

    public ItemRendererCrossingGate()
    {
        this.te = new TileEntityCrossingGate();
        //this.te.setRotation(2);
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return item.getItem() == Item.getItemFromBlock(FuelcraftBlocks.blockCrossingGate);
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        GL11.glPushMatrix();

        switch(type)
        {
            case INVENTORY:
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glScalef(16.0f, 14.0f, 16.0f);
                GL11.glRotatef(180f, 1f, 0f, 0f);
                GL11.glRotatef(45f, 0f, 1f, 0f);
                GL11.glRotatef(-30f, 1f, 0f, 1f);
                GL11.glTranslatef(0.1f, -0.4f, 0.1f);
                break;
            default:
        }

        TileEntityRendererDispatcher.instance.renderTileEntityAt(this.te, 0.0d, 0.0d, 0.0d, 0.0f);

        GL11.glPopMatrix();
    }
}
