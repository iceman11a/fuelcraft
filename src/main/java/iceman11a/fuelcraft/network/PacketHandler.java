package iceman11a.fuelcraft.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import iceman11a.fuelcraft.reference.Reference;

public class PacketHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID.toLowerCase());

    public static void init()
    {
        INSTANCE.registerMessage(MessageCrossingGateSetArea.class, MessageCrossingGateSetArea.class, 0, Side.SERVER);
        INSTANCE.registerMessage(MessageGuiAction.class, MessageGuiAction.class, 1, Side.SERVER);
    }
}
