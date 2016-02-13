package iceman11a.fuelcraft.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.client.MinecraftForgeClient;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.ReflectionHelper;

import iceman11a.fuelcraft.Fuelcraft;
import iceman11a.fuelcraft.block.FuelcraftBlocks;
import iceman11a.fuelcraft.client.renderer.item.ItemRendererCrossingGate;
import iceman11a.fuelcraft.client.renderer.tileentity.TileEntityRendererCrossingGate;
import iceman11a.fuelcraft.gui.GuiCrossingGate;
import iceman11a.fuelcraft.reference.ReferenceGuiIds;
import iceman11a.fuelcraft.reference.ReferenceReflection;
import iceman11a.fuelcraft.tileentity.TileEntityCrossingGate;

public class ClientProxy extends CommonProxy
{
    @Override
    public EntityPlayer getPlayerFromMessageContext(MessageContext ctx)
    {
        switch (ctx.side)
        {
            case CLIENT:
                return FMLClientHandler.instance().getClientPlayerEntity();

            case SERVER:
                return ctx.getServerHandler().playerEntity;

            default:
                Fuelcraft.logger.warn("Invalid side in getPlayerFromMessageContext(): " + ctx.side);
                return null;
        }
    }

    @Override
    public void openGui(int guiId, TileEntity te)
    {
        switch (guiId)
        {
            case ReferenceGuiIds.GUI_ID_CROSSING_GATES:
                if (te instanceof TileEntityCrossingGate)
                {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiCrossingGate((TileEntityCrossingGate)te));
                }
                break;
        }
    }

    @Override
    public void registerKeyBindings() { }

    @Override
    public void registerRenderers()
    {
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(FuelcraftBlocks.blockCrossingGate), new ItemRendererCrossingGate());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrossingGate.class, new TileEntityRendererCrossingGate());
    }

    @Override
    public void setupReflection()
    {
        ReferenceReflection.fieldGuiContainer_theSlot = ReflectionHelper.findField(GuiContainer.class, "u", "field_147006_u", "theSlot");
    }
}
