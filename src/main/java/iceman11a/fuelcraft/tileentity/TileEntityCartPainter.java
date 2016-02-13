package iceman11a.fuelcraft.tileentity;

import iceman11a.fuelcraft.gui.GuiCartPainter;
import iceman11a.fuelcraft.gui.GuiFuelCraftInventory;
import iceman11a.fuelcraft.inventory.ContainerCartPainter;
import iceman11a.fuelcraft.reference.ReferenceNames;

import java.util.ArrayList;
import java.util.List;

import mods.railcraft.common.carts.CartUtils;
import mods.railcraft.common.carts.EntityLocomotive;
import mods.railcraft.common.carts.ItemLocomotive;
import mods.railcraft.common.util.misc.EnumColor;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityCartPainter extends TileEntityFuelCraftInventory
{
    public static final int SLOT_ENGINE_FILTER = 0;
    public static final int SLOT_DYE_PRIMARY   = 1;
    public static final int SLOT_DYE_SECONDARY = 2;

    protected List<Integer> entityIds = new ArrayList<Integer>();
    private boolean redstoneState;

    public TileEntityCartPainter()
    {
        super(ReferenceNames.NAME_TILE_CART_PAINTER);
        this.itemStacks = new ItemStack[3];
    }

    @Override
    public void onBlockNeighbourChange()
    {
        this.redstoneState = this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord);
    }

    @Override
    public void updateEntity()
    {
        if (this.worldObj.isRemote == false && this.redstoneState == true)
        {
            this.triggerAction();
        }
    }

    @SuppressWarnings("unchecked")
    public boolean triggerAction()
    {
        EnumColor primaryColor = this.itemStacks[SLOT_DYE_PRIMARY] != null ? EnumColor.fromDye(this.getDyeTag(this.itemStacks[SLOT_DYE_PRIMARY])) : null;
        EnumColor secondaryColor = this.itemStacks[SLOT_DYE_SECONDARY] != null ? EnumColor.fromDye(this.getDyeTag(this.itemStacks[SLOT_DYE_SECONDARY])) : null;

        ForgeDirection dir = ForgeDirection.getOrientation(this.getRotation());
        int x = this.xCoord + dir.offsetX;
        int y = this.yCoord;
        int z = this.zCoord + dir.offsetZ;

        boolean ret = false;
        double r = 1.0d;
        AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(x, y, z, x + r, y + r, z + r);
        List<EntityLocomotive> list = this.worldObj.getEntitiesWithinAABB(EntityLocomotive.class, aabb);

        // Clear the list of "already-painted-carts" when there are no carts nearby
        if (list.isEmpty() == true && this.entityIds.isEmpty() == false)
        {
            this.entityIds.clear();
        }

        for (EntityLocomotive loco : list)
        {
            if (this.entityIds.contains(loco.getEntityId()) == false &&
               (this.itemStacks[SLOT_ENGINE_FILTER] == null || CartUtils.doesCartMatchFilter(this.itemStacks[SLOT_ENGINE_FILTER], loco) == true))
            {
                if (primaryColor != null)
                {
                    loco.setPrimaryColor(primaryColor.ordinal());
                    ret = true;
                }

                if (secondaryColor != null)
                {
                    loco.setSecondaryColor(secondaryColor.ordinal());
                    ret = true;
                }

                this.entityIds.add(loco.getEntityId());
            }
        }

        return ret;
    }

    /**
     * Returns the OreDictionary dye name of the item in slot slotNum, or an empty string if there is no item or the item is not a registered dye.
     * @param slotNum
     * @return The OreDictionary dye name, or empty string
     */
    protected String getDyeTag(ItemStack stack)
    {
        for (int id : OreDictionary.getOreIDs(stack))
        {
            String oreName = OreDictionary.getOreName(id);
            // Check that we are not returning the generic name "dye" which also exists
            if (oreName.startsWith("dye") && oreName.length() > 3)
            {
                return oreName;
            }
        }
        return "";
    }

    @Override
    public boolean isItemValidForSlot(int slotNum, ItemStack stack)
    {
        if (stack == null)
        {
            return true;
        }

        if (slotNum == SLOT_ENGINE_FILTER)
        {
            return stack.getItem() instanceof ItemLocomotive;
        }
        // Dye slots
        else
        {
            for (int id : OreDictionary.getOreIDs(stack))
            {
                if (OreDictionary.getOreName(id).startsWith("dye"))
                {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public ContainerCartPainter getContainer(InventoryPlayer inventoryPlayer)
    {
        return new ContainerCartPainter(inventoryPlayer, this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public GuiFuelCraftInventory getGui(InventoryPlayer inventoryPlayer)
    {
        return new GuiCartPainter(this.getContainer(inventoryPlayer), this);
    }
}
