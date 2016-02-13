package iceman11a.fuelcraft.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import iceman11a.fuelcraft.tileentity.TileEntityCrossingGate;
import io.netty.buffer.ByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MessageCrossingGateSetArea implements IMessage, IMessageHandler<MessageCrossingGateSetArea, IMessage>
{
    private int dimension;
    private int posX;
    private int posY;
    private int posZ;
    private byte[] bounds = new byte[6];

    public MessageCrossingGateSetArea()
    {
    }

    public MessageCrossingGateSetArea(int dim, int x, int y, int z, byte[] area)
    {
        this.dimension = dim;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.bounds = area;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.dimension = buf.readInt();
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
        buf.readBytes(this.bounds, 0, 6);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.dimension);
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
        buf.writeBytes(this.bounds);
    }

    @Override
    public IMessage onMessage(MessageCrossingGateSetArea message, MessageContext ctx)
    {
        World world = MinecraftServer.getServer().worldServerForDimension(message.dimension);
        if (world != null)
        {
            TileEntity te = world.getTileEntity(message.posX, message.posY, message.posZ);
            if (te instanceof TileEntityCrossingGate)
            {
                ((TileEntityCrossingGate)te).setArea(message.bounds);
                world.markBlockForUpdate(message.posX, message.posY, message.posZ);
            }
        }
        return null;
    }
}
