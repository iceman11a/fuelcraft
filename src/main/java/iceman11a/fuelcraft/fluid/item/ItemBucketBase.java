package iceman11a.fuelcraft.fluid.item;

import iceman11a.fuelcraft.fuelcraft;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;

public class ItemBucketBase extends ItemBucket
{
	public ItemBucketBase(Block fluidBlock)
	{
		super(fluidBlock);
		setMaxStackSize(1);
		setContainerItem(Items.bucket);
		setCreativeTab(fuelcraft.tabFuelcraft);
		this.setTextureName("fc:dieselFuelBucket");
	}
}
