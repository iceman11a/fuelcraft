package iceman11a.fuelcraft.block.machine;

import java.util.List;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceman11a.fuelcraft.Fuelcraft;
import iceman11a.fuelcraft.block.BlockFuelcraftBase;
import iceman11a.fuelcraft.reference.ReferenceGuiIds;
import iceman11a.fuelcraft.reference.ReferenceNames;
import iceman11a.fuelcraft.reference.ReferenceTextures;
import iceman11a.fuelcraft.tileentity.TileEntityCartFilter;
import iceman11a.fuelcraft.tileentity.TileEntityCartPainter;
import iceman11a.fuelcraft.tileentity.TileEntityDieselProducer;
import iceman11a.fuelcraft.tileentity.TileEntityFuelCraft;
import iceman11a.fuelcraft.tileentity.TileEntityOilProducer;
import iceman11a.fuelcraft.tileentity.TileEntityTapoilProducer;
import iceman11a.fuelcraft.tileentity.TileEntityTokenController;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class BlockMachine extends BlockFuelcraftBase
{
    public static final byte YAW_TO_DIRECTION[] = {2, 5, 3, 4};

    public BlockMachine(String name, float hardness, Material material)
    {
        super(name, hardness, material);
    }

    @Override
    public boolean hasTileEntity(int metadata)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        switch (metadata)
        {
            case 0:
                return new TileEntityDieselProducer();
            case 1:
                return new TileEntityCartPainter();
            case 2:
                return new TileEntityTokenController();
            case 3:
                return new TileEntityTapoilProducer();
            case 4:
                return new TileEntityOilProducer();
            case 5:
                return new TileEntityCartFilter();
        }

        return null;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase livingBase, ItemStack stack)
    {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te == null || (te instanceof TileEntityFuelCraft) == false)
        {
            return;
        }

        int yaw = MathHelper.floor_double((double)(livingBase.rotationYaw * 4.0f / 360.0f) + 0.5d) & 3;
        // Store the rotation to the TileEntity
        if (yaw < YAW_TO_DIRECTION.length)
        {
            ((TileEntityFuelCraft)te).setRotation(YAW_TO_DIRECTION[yaw]);
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float offsetX, float offsetY, float offsetZ)
    {
        PlayerInteractEvent e = new PlayerInteractEvent(player, PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK, x, y, z, side, world);
        if (MinecraftForge.EVENT_BUS.post(e) || e.getResult() == Result.DENY || e.useBlock == Result.DENY)
        {
            return false;
        }

        if (world.isRemote == false)
        {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te == null || te instanceof TileEntityFuelCraft == false)
            {
                return false;
            }

            player.openGui(Fuelcraft.instance, ReferenceGuiIds.GUI_ID_TILE_ENTITY_GENERIC, world, x, y, z);
        }

        return true;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        super.onNeighborBlockChange(world, x, y, z, block);

        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityFuelCraft)
        {
            ((TileEntityFuelCraft)te).onBlockNeighbourChange();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        list.add(new ItemStack(this, 1, 0)); // Diesel Producer
        list.add(new ItemStack(this, 1, 1)); // Cart Painter
        list.add(new ItemStack(this, 1, 2)); // Token Controller
        list.add(new ItemStack(this, 1, 3)); // Tapoil Producer
        list.add(new ItemStack(this, 1, 4)); // Oil Producer
        list.add(new ItemStack(this, 1, 5)); // Cart Filter
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        // These are for the rendering in ItemBlock form in inventories etc.

        int index = 0;
        int offset = meta * 6;

        if (side == 0 || side == 1)
        {
            index = offset + 1; // top
        }
        else if (side == 3)
        {
            index = offset + 2; // front
        }
        else
        {
            index = offset + 4; // side (left)
        }

        return index < this.icons.length ? this.icons[index] : this.blockIcon;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side)
    {
        int index = 0;
        int meta = blockAccess.getBlockMetadata(x, y, z);
        int offset = meta * 6;

        // Bottom or top
        if (side == 0 || side == 1)
        {
            index = offset + side;
        }
        else
        {
            // 0: down, 1: up, 2: north, 3: south, 4: west, 5: east
            // 0: bottom, 1: top, 2: front, 3: back, 4: right, 5: left

            int rotIndex[] = {0, 0, 0, 2, 3, 1};
            int rotations[][] = {
                                    {0, 1, 2, 3, 5, 4}, // north
                                    {0, 1, 5, 4, 3, 2}, // east
                                    {0, 1, 3, 2, 4, 5}, // south
                                    {0, 1, 4, 5, 2, 3} // west
                                };

            TileEntity te = blockAccess.getTileEntity(x, y, z);
            // Get the rotation of the block to decide when to return the front texture, and when the sides
            if (te instanceof TileEntityFuelCraft)
            {
                index = offset + rotations[rotIndex[ForgeDirection.getOrientation(((TileEntityFuelCraft)te).getRotation()).ordinal()]][side];
            }
        }

        return index < this.icons.length ? this.icons[index] : this.blockIcon;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.icons = new IIcon[36];
        this.blockIcon = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_DIESEL_PRODUCER) + ".top"); // Fallback

        // Diesel Producer
        this.icons[0] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_DIESEL_PRODUCER) + ".bottom");
        this.icons[1] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_DIESEL_PRODUCER) + ".top");
        this.icons[2] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_DIESEL_PRODUCER) + ".front");
        this.icons[3] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_DIESEL_PRODUCER) + ".back");
        this.icons[4] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_DIESEL_PRODUCER) + ".right");
        this.icons[5] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_DIESEL_PRODUCER) + ".left");

        // Cart Painter
        this.icons[6] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_CART_PAINTER) + ".bottom");
        this.icons[7] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_CART_PAINTER) + ".top");
        this.icons[8] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_CART_PAINTER) + ".front");
        this.icons[9] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_CART_PAINTER) + ".back");
        this.icons[10] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_CART_PAINTER) + ".right");
        this.icons[11] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_CART_PAINTER) + ".left");

        // Token Controller
        this.icons[12] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_TOKEN_CONTROLLER) + ".bottom");
        this.icons[13] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_TOKEN_CONTROLLER) + ".top");
        this.icons[14] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_TOKEN_CONTROLLER) + ".front");
        this.icons[15] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_TOKEN_CONTROLLER) + ".back");
        this.icons[16] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_TOKEN_CONTROLLER) + ".right");
        this.icons[17] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_TOKEN_CONTROLLER) + ".left");

        // Tapoil Producer
        this.icons[18] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_TAPOIL_PRODUCER) + ".bottom");
        this.icons[19] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_TAPOIL_PRODUCER) + ".top");
        this.icons[20] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_TAPOIL_PRODUCER) + ".front");
        this.icons[21] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_TAPOIL_PRODUCER) + ".back");
        this.icons[22] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_TAPOIL_PRODUCER) + ".right");
        this.icons[23] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_TAPOIL_PRODUCER) + ".left");

        // Oil Producer
        this.icons[24] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_OIL_PRODUCER) + ".bottom");
        this.icons[25] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_OIL_PRODUCER) + ".top");
        this.icons[26] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_OIL_PRODUCER) + ".front");
        this.icons[27] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_OIL_PRODUCER) + ".back");
        this.icons[28] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_OIL_PRODUCER) + ".right");
        this.icons[29] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_OIL_PRODUCER) + ".left");
        
        // Cart Filter
        this.icons[30] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_CART_FILTER) + ".bottom");
        this.icons[31] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_CART_FILTER) + ".top");
        this.icons[32] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_CART_FILTER) + ".front");
        this.icons[33] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_CART_FILTER) + ".back");
        this.icons[34] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_CART_FILTER) + ".right");
        this.icons[35] = iconRegister.registerIcon(ReferenceTextures.getTileName(ReferenceNames.NAME_TILE_CART_FILTER) + ".left");
    }
}
