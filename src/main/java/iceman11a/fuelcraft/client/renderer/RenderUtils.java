package iceman11a.fuelcraft.client.renderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;

public class RenderUtils
{
    public static void renderAABB(AxisAlignedBB aabb) {

        double offset = 0.006;

        Tessellator t = Tessellator.instance;

        t.startDrawingQuads();
        //t.startDrawing(GL11.GL_LINES);
        //t.setNormal(0.0f, 0.0f, -1.0f);
        t.addVertex(aabb.maxX - offset, aabb.maxY - offset, aabb.minZ + offset);
        t.addVertex(aabb.minX + offset, aabb.maxY - offset, aabb.minZ + offset);
        t.addVertex(aabb.minX + offset, aabb.minY + offset, aabb.minZ + offset);
        t.addVertex(aabb.maxX - offset, aabb.minY + offset, aabb.minZ + offset);
        //t.draw();

        //t.startDrawingQuads();
        //t.startDrawing(GL11.GL_LINES);
        //t.setNormal(0.0f, 0.0f, 1.0f);
        t.addVertex(aabb.minX + offset, aabb.maxY - offset, aabb.maxZ - offset);
        t.addVertex(aabb.maxX - offset, aabb.maxY - offset, aabb.maxZ - offset);
        t.addVertex(aabb.maxX - offset, aabb.minY + offset, aabb.maxZ - offset);
        t.addVertex(aabb.minX + offset, aabb.minY + offset, aabb.maxZ - offset);
        //t.draw();

        //t.startDrawingQuads();
        //t.startDrawing(GL11.GL_LINES);
        //t.setNormal(0.0f, -1.0f, 0.0f);
        t.addVertex(aabb.minX + offset, aabb.minY + offset, aabb.maxZ - offset);
        t.addVertex(aabb.maxX - offset, aabb.minY + offset, aabb.maxZ - offset);
        t.addVertex(aabb.maxX - offset, aabb.minY + offset, aabb.minZ + offset);
        t.addVertex(aabb.minX + offset, aabb.minY + offset, aabb.minZ + offset);
        //t.draw();

        //t.startDrawingQuads();
        //t.startDrawing(GL11.GL_LINES);
        //t.setNormal(0.0f, 1.0f, 0.0f);
        t.addVertex(aabb.minX + offset, aabb.maxY - offset, aabb.minZ + offset);
        t.addVertex(aabb.maxX - offset, aabb.maxY - offset, aabb.minZ + offset);
        t.addVertex(aabb.maxX - offset, aabb.maxY - offset, aabb.maxZ - offset);
        t.addVertex(aabb.minX + offset, aabb.maxY - offset, aabb.maxZ - offset);
        //t.draw();

        //t.startDrawingQuads();
        //t.startDrawing(GL11.GL_LINES);
        //t.setNormal(-1.0f, 0.0f, 0.0f);
        t.addVertex(aabb.minX + offset, aabb.maxY - offset, aabb.minZ + offset);
        t.addVertex(aabb.minX + offset, aabb.maxY - offset, aabb.maxZ - offset);
        t.addVertex(aabb.minX + offset, aabb.minY + offset, aabb.maxZ - offset);
        t.addVertex(aabb.minX + offset, aabb.minY + offset, aabb.minZ + offset);
        //t.draw();

        //t.startDrawingQuads();
        //t.startDrawing(GL11.GL_LINES);
        //t.setNormal(1.0f, 0.0f, 0.0f);
        t.addVertex(aabb.maxX - offset, aabb.maxY - offset, aabb.maxZ - offset);
        t.addVertex(aabb.maxX - offset, aabb.maxY - offset, aabb.minZ + offset);
        t.addVertex(aabb.maxX - offset, aabb.minY + offset, aabb.minZ + offset);
        t.addVertex(aabb.maxX - offset, aabb.minY + offset, aabb.maxZ - offset);
        t.draw();
    }
}
