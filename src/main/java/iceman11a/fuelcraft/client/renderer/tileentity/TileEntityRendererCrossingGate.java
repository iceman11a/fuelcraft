package iceman11a.fuelcraft.client.renderer.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import iceman11a.fuelcraft.client.model.ModelCrossingGate;
import iceman11a.fuelcraft.client.renderer.RenderUtils;
import iceman11a.fuelcraft.reference.Reference;
import iceman11a.fuelcraft.tileentity.TileEntityCrossingGate;

public class TileEntityRendererCrossingGate extends TileEntitySpecialRenderer
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/model/crossing_gate.png");
    private ModelCrossingGate model = new ModelCrossingGate();
    private float r = 0.0f;
    private float g = 0.4f;
    private float b = 0.8f;
    private float a = 0.9f;

    private void setColors(float r, float g, float b, float a)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public void renderArea(double x, double y, double z, AxisAlignedBB aabb, float pTicks)
    {
        //GL11.glColorMask(true, true, true, true);
        GL11.glBlendFunc(GL11.GL_DST_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);

        //GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);

        //GL11.glEnable(GL11.GL_ALPHA_TEST);
        //GL11.glDisable(GL11.GL_FOG);
        //GL11.glEnable(GL11.GL_BLEND);
        //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        //GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glPushMatrix();
        //GL11.glColor4f(0.0f, 0.4f, 0.8f, 0.6f);

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        float playerOffsetX = -(float) (player.lastTickPosX + (player.posX - player.lastTickPosX) * pTicks);
        float playerOffsetY = -(float) (player.lastTickPosY + (player.posY - player.lastTickPosY) * pTicks);
        float playerOffsetZ = -(float) (player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * pTicks);

        //GL11.glTranslated(x, y, z);
        GL11.glTranslatef(playerOffsetX, playerOffsetY, playerOffsetZ);

        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        GL11.glColor4f(this.r, this.g, this.b, 1.0f);

        RenderUtils.renderAABB(aabb);

        GL11.glColor4f(this.r, this.g, this.b, this.a);
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
        GL11.glEnable(GL11.GL_BLEND);

        RenderUtils.renderAABB(aabb);
        //System.out.printf("%6.2f %6.2f %6.2f %6.2f %6.2f %6.2f\n", aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ);

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_POLYGON_OFFSET_LINE);
        GL11.glPolygonOffset(-1.f, -1.f);

        //GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public void renderBaseAndGate(double x, double y, double z, int facing, float angle, float pTicks)
    {
        this.bindTexture(TEXTURE);

        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        //GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glTranslated(x, y, z);

        float rotation = 0.0f;

        switch (facing) {
            case 2: // North
                rotation = 180.0f;
                break;
            case 3: // South
                rotation = 0.0f;
                break;
            case 4: // West
                rotation = -90.0f;
                break;
            case 5: // East
                rotation = 90.0f;
                break;
        }

        GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f);

        this.model.gate.rotateAngleZ = (float)(angle * Math.PI / 180.0f);
        this.model.renderAll();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
    }

    public void renderInInventory(double x, double y, double z, int facing, float pTicks)
    {
        this.bindTexture(TEXTURE);

        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glTranslatef(0.5f, 0.5f, 0f);
        //GL11.glRotatef(0f, 0.0f, 1.0f, 0.0f);

        this.model.renderBase();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }

    public void updateAngle(TileEntityCrossingGate te)
    {
        float moveTimeMs = 2000;

        if (te.movingToLast != te.movingTo)
        {
            te.startAngle = te.currentAngle;
            te.movingToLast = te.movingTo;
        }

        if (te.movingTo != TileEntityCrossingGate.STATE_NOT_MOVING)
        {
            float angleDiff = ((System.currentTimeMillis() - te.timeStart) * 90 / moveTimeMs);

            // moveTimeMs to move from one end position to other end position
            if (te.movingTo == TileEntityCrossingGate.STATE_OPEN)
            {
                te.currentAngle = te.startAngle - angleDiff;
            }
            else if (te.movingTo == TileEntityCrossingGate.STATE_CLOSED)
            {
                te.currentAngle = te.startAngle + angleDiff;
            }

            te.currentAngle = MathHelper.clamp_float(te.currentAngle, 0.0f, 90.0f);

            if ((System.currentTimeMillis() - te.timeStart) > 100 && (te.currentAngle == 0.0f || te.currentAngle == 90.0f))
            {
                te.movingTo = TileEntityCrossingGate.STATE_NOT_MOVING;
                te.movingToLast = te.movingTo;
            }
        }
    }

    public void renderTileEntityAt(TileEntityCrossingGate te, double x, double y, double z, float pTicks)
    {
        // In inventory
        if (te.getRotation() == 0)
        {
            this.renderInInventory(x, y, z, 2, pTicks);
            return;
        }

        this.renderBaseAndGate(x + 0.5d, y + 1.0d, z + 0.5d, te.getRotation(), te.currentAngle, pTicks);
        this.updateAngle(te);

        if (te.renderArea == true)
        {
            AxisAlignedBB bb = te.getEditArea();
            if (bb != null)
            {
                bb = te.getAbsoluteArea(bb);
                this.setColors(0.8f, 0.0f, 0.4f, 0.4f);
                this.renderArea(x, y, z, bb, pTicks);
            }
            else
            {
                bb = te.getAbsoluteArea();
                if (bb != null)
                {
                    this.setColors(0.0f, 0.4f, 0.8f, 0.4f);
                    this.renderArea(x, y, z, bb, pTicks);
                }
            }
        }
    }

    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f)
    {
        this.renderTileEntityAt((TileEntityCrossingGate)te, x, y, z, f);
    }
}
