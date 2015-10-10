package iceman11a.fuelcraft.block.machine;

import iceman11a.fuelcraft.Fuelcraft;
import iceman11a.fuelcraft.block.BlockFuelcraftBase;
import iceman11a.fuelcraft.reference.ReferenceGuiIds;
import iceman11a.fuelcraft.reference.ReferenceNames;
import iceman11a.fuelcraft.reference.ReferenceTextures;
import iceman11a.fuelcraft.tileentity.TileEntityCartPainter;
import iceman11a.fuelcraft.tileentity.TileEntityDieselProducer;
import iceman11a.fuelcraft.tileentity.TileEntityFuelCraft;

import java.util.List;

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
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDieselProducer extends BlockFuelcraftBase {

	public static final byte YAW_TO_DIRECTION[] = {2, 5, 3, 4};

	public BlockDieselProducer(String name, float hardness, Material material)
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		list.add(new ItemStack(this, 1, 0)); // Diesel Producer
		list.add(new ItemStack(this, 1, 1)); // Cart Painter
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta)
	{
		// These are for the rendering in ItemBlock form in inventories etc.

		int offset = meta * 6;
		if (side == 0 || side == 1)
		{
			return this.icons[offset + 1]; // top
		}
		if (side == 3)
		{
			return this.icons[offset + 2]; // front
		}

		return this.icons[offset + 4]; // side (left)
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side)
	{
		int meta = blockAccess.getBlockMetadata(x, y, z);
		int offset = meta * 6;

		// Bottom or top
		if (side == 0 || side == 1)
		{
			return this.icons[offset + side];
		}

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
			int rot = ((TileEntityFuelCraft)te).getRotation();
			side = rotations[rotIndex[ForgeDirection.getOrientation(rot).ordinal()]][side];
			return this.icons[offset + side];
		}

		return this.icons[offset + side];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		this.icons = new IIcon[12];
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
	}
}
