package iceman11a.fuelcraft.item;

import iceman11a.fuelcraft.Fuelcraft;
import iceman11a.fuelcraft.block.fluid.FuelcraftFluids;
import iceman11a.fuelcraft.reference.ReferenceNames;
import iceman11a.fuelcraft.reference.ReferenceTextures;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;

public class ItemDieselBucket extends ItemBucket
{
    public ItemDieselBucket()
    {
        this(FuelcraftFluids.blockFluidDiesel);
    }

    public ItemDieselBucket(Block fluidBlock)
    {
        super(fluidBlock);
        setMaxStackSize(1);
        setContainerItem(Items.bucket);
        setCreativeTab(Fuelcraft.tabFuelcraft);
        this.setUnlocalizedName(ReferenceNames.getPrefixedName(ReferenceNames.NAME_ITEM_DIESEL_BUCKET));
        this.setTextureName(ReferenceTextures.getItemTextureName(ReferenceNames.NAME_ITEM_DIESEL_BUCKET));
    }
}
