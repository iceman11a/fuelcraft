package iceman11a.fuelcraft.fluid.item;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import iceman11a.fuelcraft.fcItems.BucketDieselFuel;
import iceman11a.fuelcraft.fcItems.fcItems;

public class ItemBucketDiesel extends ItemBucketBase
{
	public int hash;
	private Item vanillaDieselBucket;

	public ItemBucketDiesel(Block fluidBlock)
	{
		super(fluidBlock);
		this.setUnlocalizedName("BucketDieselFuel");
		//this.setTextureName("fc:BucketDieselFuel");
		this.vanillaDieselBucket = fcItems.BucketDieselFuel; //Items.milk_bucket;
		this.hash =  this.vanillaDieselBucket.hashCode();
	}

	@Override
	public int hashCode()
	{
		return this.vanillaDieselBucket.hashCode();                
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj == this.vanillaDieselBucket || obj == this;
	}
}
