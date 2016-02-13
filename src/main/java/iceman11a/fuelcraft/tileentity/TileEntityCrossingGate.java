package iceman11a.fuelcraft.tileentity;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import iceman11a.fuelcraft.reference.ReferenceNames;

public class TileEntityCrossingGate extends TileEntityFuelCraft
{
    public AxisAlignedBB renderBB;
    public static final int STATE_NOT_MOVING = 0;
    public static final int STATE_OPEN       = 1;
    public static final int STATE_CLOSED     = 2;

    protected AxisAlignedBB areaRelative;
    protected AxisAlignedBB areaAbsolute;
    public boolean isCartsMode;
    protected boolean statusLast;
    //protected boolean redstoneState;

    @SideOnly(Side.CLIENT)
    protected AxisAlignedBB areaEdit;
    @SideOnly(Side.CLIENT)
    public boolean renderArea;
    @SideOnly(Side.CLIENT)
    public int movingTo;
    @SideOnly(Side.CLIENT)
    public int movingToLast;
    @SideOnly(Side.CLIENT)
    public long timeStart;
    @SideOnly(Side.CLIENT)
    public float startAngle;
    @SideOnly(Side.CLIENT)
    public float currentAngle;

    public TileEntityCrossingGate()
    {
        super(ReferenceNames.NAME_TILE_CROSSING_GATE);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        this.isCartsMode = nbt.getByte("Mode") == 1;
        //this.redstoneState = nbt.getByte("Redstone") == 1;

        if (nbt.hasKey("RelBB", Constants.NBT.TAG_BYTE_ARRAY) == true)
        {
            this.setArea(nbt.getByteArray("RelBB"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        nbt.setByte("Mode", (byte)(this.isCartsMode == true ? 1 : 0));
        //nbt.setByte("Redstone", (byte)(this.redstoneState == true ? 1 : 0));
        nbt.setByteArray("RelBB", this.getAreaAsArray(this.getRelativeArea()));
    }

    @Override
    public void onBlockNeighbourChange()
    {
        if (this.isCartsMode == true)
        {
            this.checkForCarts();
        }
        else
        {
            this.checkRedstone();
        }
    }

    protected void checkForCarts()
    {
        boolean carts = this.worldObj.getEntitiesWithinAABB(EntityMinecart.class, this.getAbsoluteArea()).isEmpty() == false;

        if (carts != this.statusLast)
        {
            this.statusLast = carts;
            this.getWorldObj().markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
    }

    protected void checkRedstone()
    {
        boolean state = this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord);

        if (state != this.statusLast)
        {
            this.statusLast = state;
            this.getWorldObj().markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
    }

    @Override
    public NBTTagCompound getDescriptionPacketTag(NBTTagCompound nbt)
    {
        nbt = super.getDescriptionPacketTag(nbt);

        nbt.setBoolean("status", this.statusLast);
        nbt.setBoolean("mode", this.isCartsMode);
        nbt.setByteArray("bb", this.getAreaAsArray(this.getRelativeArea()));

        return nbt;
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
    {
        super.onDataPacket(net, packet);

        NBTTagCompound nbt = packet.func_148857_g();

        this.isCartsMode = nbt.getBoolean("mode");
        boolean status = nbt.getBoolean("status");

        if (status != this.statusLast)
        {
            if (status == true)
            {
                this.movingTo = STATE_CLOSED;
            }
            else
            {
                this.movingTo = STATE_OPEN;
            }

            this.timeStart = System.currentTimeMillis();
            this.statusLast = status;
        }

        if (nbt.hasKey("bb", Constants.NBT.TAG_BYTE_ARRAY))
        {
            byte[] bb = nbt.getByteArray("bb");
            if (bb.length == 6)
            {
                this.setArea(bb[0], bb[1], bb[2], bb[3], bb[4], bb[5]);
            }
        }
    }

    @Override
    public void setRotation(int rot)
    {
        super.setRotation(rot);

        this.initArea();
    }

    public byte[] getAreaAsArray(AxisAlignedBB bb)
    {
        return new byte[] {
                (byte)bb.minX,
                (byte)bb.minY,
                (byte)bb.minZ,
                (byte)bb.maxX,
                (byte)bb.maxY,
                (byte)bb.maxZ
            };
    }

    public void initArea()
    {
        this.setArea(this.getDefaultArea());
    }

    /**
     * Returns the default area in <b>relative coordinates</b>.
     */
    public AxisAlignedBB getDefaultArea()
    {
        ForgeDirection dir = ForgeDirection.getOrientation(this.getRotation()).getOpposite();
        ForgeDirection dirP = dir.getRotation(ForgeDirection.UP);
        AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        aabb = aabb.expand(Math.abs(dirP.offsetX) * 6.0d + Math.abs(dir.offsetX) * 2.0d, 0.0d, Math.abs(dirP.offsetZ) * 6.0d + Math.abs(dir.offsetZ) * 2.0d);
        aabb = aabb.offset(dir.offsetX * 3.0d, 0.0d, dir.offsetZ * 3.0d);
        return aabb;
    }

    /**
     * Sets the cart scanning area from the <b>relative coordinate</b> bounding box <b>area</b>.
     * @param area
     */
    public void setArea(AxisAlignedBB area)
    {
        this.setArea(area.minX, area.minY, area.minZ, area.maxX, area.maxY, area.maxZ);
    }

    /**
     * Sets the cart scanning area from the given <b>relative coordinates</b>.
     */
    public void setArea(byte[] area)
    {
        if (area != null && area.length == 6)
        {
            this.setArea(area[0], area[1], area[2], area[3], area[4], area[5]);
        }
    }

    /**
     * Sets the cart scanning area from the given <b>relative coordinates</b>.
     */
    public void setArea(double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
    {
        double r = 32.0d;
        minX = MathHelper.clamp_double(minX, -r, r);
        minY = MathHelper.clamp_double(minY, -r, r);
        minZ = MathHelper.clamp_double(minZ, -r, r);
        maxX = MathHelper.clamp_double(maxX, -r, r);
        maxY = MathHelper.clamp_double(maxY, -r, r);
        maxZ = MathHelper.clamp_double(maxZ, -r, r);

        this.areaRelative = AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
        this.areaAbsolute = this.getAbsoluteArea(this.areaRelative);
    }

    public AxisAlignedBB getAbsoluteArea(AxisAlignedBB bb)
    {
        return AxisAlignedBB.getBoundingBox(
                    bb.minX + this.xCoord,
                    bb.minY + this.yCoord,
                    bb.minZ + this.zCoord,
                    bb.maxX + this.xCoord,
                    bb.maxY + this.yCoord,
                    bb.maxZ + this.zCoord);
    }

    /**
     * @return the current <b>relative coordinate area</b> for scanning for carts
     */
    public AxisAlignedBB getRelativeArea()
    {
        if (this.areaRelative == null)
        {
            this.initArea();
        }

        return this.areaRelative;
    }

    /**
     * @return the current <b>absolute/real coordinate area</b> for scanning for carts
     */
    public AxisAlignedBB getAbsoluteArea()
    {
        if (this.areaAbsolute == null)
        {
            this.initArea();
        }

        return this.areaAbsolute;
    }

    /**
     * Sets the secondary, "being-edited" area from the <b>relative coordinate</b> bounding box <b>area</b>.
     * @param area
     */
    public void setEditArea(AxisAlignedBB area)
    {
        this.areaEdit = area;
    }

    /**
     * @return the <b>relative coordinate</b> area being edited
     */
    public AxisAlignedBB getEditArea()
    {
        return this.areaEdit;
    }

    @Override
    public void performGuiAction(EntityPlayer player, int action, int element)
    {
        if (action == 0 && element == 0)
        {
            this.isCartsMode = ! this.isCartsMode;
            this.getWorldObj().markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        if (this.renderBB == null)
        {
            double r = 32d;
            this.renderBB = AxisAlignedBB.getBoundingBox(this.xCoord - r, this.yCoord - r, this.zCoord - r, this.xCoord + r, this.yCoord + r, this.zCoord + r);
        }

        return this.renderBB;
    }
}
