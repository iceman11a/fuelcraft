package iceman11a.fuelcraft.machines;

import cpw.mods.fml.common.event.FMLMissingMappingsEvent.Action;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceman11a.fuelcraft.fuelcraft;
import iceman11a.fuelcraft.tileentity.TileEntityDieselProducer;
import iceman11a.fuelcraft.tileentity.TileEntityFuelCraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class BlockDieselProducer extends Block {	

	 public static final byte YAW_TO_DIRECTION[] = {2, 5, 3, 4};

	    public String blockName;

	    @SideOnly(Side.CLIENT)
	    protected IIcon[] icons;

	    public BlockDieselProducer(String name, float hardness)
	    {
	        this(name, hardness, Material.rock);
	        this.setStepSound(soundTypeStone);
	    }

	    public BlockDieselProducer(String name, float hardness, Material material)
	    {
	        super(material);
	        this.setHardness(hardness);
	        this.setCreativeTab(fuelcraft.tabFuelcraft);
	        this.setBlockName(name);
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

	    @Override
	    public boolean hasTileEntity(int metadata)
	    {
	        return true;
	    }

	    @Override
	    public TileEntity createTileEntity(World world, int metadata)
	    {
	        return new TileEntityDieselProducer();
	    }

	    @Override
	    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase livingBase, ItemStack stack)
	    {
	        TileEntity te = world.getTileEntity(x, y, z);
	        if (te == null || (te instanceof TileEntityDieselProducer) == false)
	        {
	            return;
	        }
	        	//System.out.println("onblockplaced by");
	        int yaw = MathHelper.floor_double((double)(livingBase.rotationYaw * 4.0f / 360.0f) + 0.5d) & 3;
	        // Store the rotation to the TileEntity
	        if (yaw < YAW_TO_DIRECTION.length)
	        {
	            ((TileEntityDieselProducer)te).setRotation(YAW_TO_DIRECTION[yaw]);
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
	            //System.out.println("Hello world");
	            player.openGui(fuelcraft.instance, ReferenceGuiIds.GUI_ID_TILE_ENTITY_GENERIC, world, x, y, z);
	        }

	        return true;
	    }
	    
	    @SideOnly(Side.CLIENT)
	    @Override
	    public IIcon getIcon(int side, int meta)
	    {
	        // These are for the rendering in ItemBlock form in inventories etc.

	        if (side == 0 || side == 1)
	        {
	            return this.icons[1]; // top
	        }
	        if (side == 3)
	        {
	            return this.icons[2]; // front
	        }

	        return this.icons[4]; // side (left)
	    }

	    @SideOnly(Side.CLIENT)
	    @Override
	    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side)
	    {
	        // FIXME we should get the proper side (left, right, back) textures based on the TE rotation and the side argument
	        if (side == 0 || side == 1)
	        {
	            return this.icons[side];
	        }

	        TileEntity te = blockAccess.getTileEntity(x, y, z);
	        // Here we should get the rotation of the block to decide when to return the front texture, and when the sides
	        if (te instanceof TileEntityDieselProducer && side == ((TileEntityDieselProducer)te).getRotation())
	        {
	            return this.icons[2]; // front
	        }

	        return this.icons[4];
	    }

	    @SideOnly(Side.CLIENT)
	    @Override
	    public void registerBlockIcons(IIconRegister iconRegister)
	    {
	        this.icons = new IIcon[6];
	        this.icons[0] = iconRegister.registerIcon(ReferenceTextures.getTileName(this.blockName) + ".side");
	        this.icons[1] = iconRegister.registerIcon(ReferenceTextures.getTileName(this.blockName) + ".side");
	        this.icons[2] = iconRegister.registerIcon(ReferenceTextures.getTileName(this.blockName) + ".front");
	        this.icons[3] = iconRegister.registerIcon(ReferenceTextures.getTileName(this.blockName) + ".side");
	        this.icons[4] = iconRegister.registerIcon(ReferenceTextures.getTileName(this.blockName) + ".side");
	        this.icons[5] = iconRegister.registerIcon(ReferenceTextures.getTileName(this.blockName) + ".side");
	    }   
	    	    
}
