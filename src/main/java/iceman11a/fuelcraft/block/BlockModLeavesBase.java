package iceman11a.fuelcraft.block;

import iceman11a.fuelcraft.Fuelcraft;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockModLeavesBase extends Block
{
    protected boolean field_150121_P;

    protected BlockModLeavesBase(Material material, boolean p_i45433_2_)
    {
        super(material);
        this.field_150121_P = p_i45433_2_;
        this.setCreativeTab(Fuelcraft.tabFuelcraft); // CreativeTabs.tabBlock);
        this.setTickRandomly(true);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        Block block = world.getBlock(x, y, z);
        return ! this.field_150121_P && block == this ? false : super.shouldSideBeRendered(world, x, y, z, side);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) { }
}