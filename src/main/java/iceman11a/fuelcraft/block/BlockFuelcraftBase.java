package iceman11a.fuelcraft.block;

import iceman11a.fuelcraft.Fuelcraft;
import iceman11a.fuelcraft.reference.ReferenceNames;
import iceman11a.fuelcraft.reference.ReferenceTextures;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFuelcraftBase extends Block
{
    public String blockName;

    @SideOnly(Side.CLIENT)
    protected IIcon[] icons;

    public BlockFuelcraftBase(String name, float hardness)
    {
        this(name, hardness, Material.rock);
        this.setStepSound(soundTypeStone);
    }

    public BlockFuelcraftBase(String name, float hardness, Material material)
    {
        super(material);
        this.setHardness(hardness);
        this.setCreativeTab(Fuelcraft.tabFuelcraft);
        this.setBlockName(name);
        this.setBlockTextureName(ReferenceTextures.getTileName(name));
    }

    @Override
    public Block setBlockName(String name)
    {
        this.blockName = name;
        return super.setBlockName(ReferenceNames.getPrefixedName(name));
    }

    @Override
    public int damageDropped(int meta)
    {
        return meta;
    }
}
