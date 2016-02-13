package iceman11a.fuelcraft.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.GameRegistry;

import iceman11a.fuelcraft.Fuelcraft;
import iceman11a.fuelcraft.reference.ReferenceNames;
import iceman11a.fuelcraft.tileentity.TileEntityCartFilter;
import iceman11a.fuelcraft.tileentity.TileEntityCartPainter;
import iceman11a.fuelcraft.tileentity.TileEntityCrossingGate;
import iceman11a.fuelcraft.tileentity.TileEntityDieselProducer;
import iceman11a.fuelcraft.tileentity.TileEntityOilProducer;
import iceman11a.fuelcraft.tileentity.TileEntityTapoilProducer;
import iceman11a.fuelcraft.tileentity.TileEntityTokenController;

public class CommonProxy
{
    public EntityPlayer getPlayerFromMessageContext(MessageContext ctx)
    {
        switch (ctx.side)
        {
            case SERVER:
                return ctx.getServerHandler().playerEntity;

            default:
                Fuelcraft.logger.warn("Invalid side in getPlayerFromMessageContext(): " + ctx.side);
                return null;
        }
    }

    public void openGui(int guiId, TileEntity te) { }
    public void registerKeyBindings() { }
    public void registerRenderers() { }

    public void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileEntityDieselProducer.class, ReferenceNames.getPrefixedName(ReferenceNames.NAME_TILE_DIESEL_PRODUCER));
        GameRegistry.registerTileEntity(TileEntityOilProducer.class, ReferenceNames.getPrefixedName(ReferenceNames.NAME_TILE_OIL_PRODUCER));
        GameRegistry.registerTileEntity(TileEntityTapoilProducer.class, ReferenceNames.getPrefixedName(ReferenceNames.NAME_TILE_TAPOIL_PRODUCER));
        GameRegistry.registerTileEntity(TileEntityCartPainter.class, ReferenceNames.getPrefixedName(ReferenceNames.NAME_TILE_CART_PAINTER));
        GameRegistry.registerTileEntity(TileEntityCartFilter.class, ReferenceNames.getPrefixedName(ReferenceNames.NAME_TILE_CART_FILTER));
        GameRegistry.registerTileEntity(TileEntityTokenController.class, ReferenceNames.getPrefixedName(ReferenceNames.NAME_TILE_TOKEN_CONTROLLER));
        GameRegistry.registerTileEntity(TileEntityCrossingGate.class, ReferenceNames.getPrefixedName(ReferenceNames.NAME_TILE_CROSSING_GATE));
    }

    public void setupReflection() { }
}
