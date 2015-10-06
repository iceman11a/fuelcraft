package iceman11a.fuelcraft.block.fcblock;

import java.util.Random;
import iceman11a.fuelcraft.fuelcraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import iceman11a.fuelcraft.fcItems.fcItems;
import net.minecraft.init.Blocks;

public class BlockCorbamiteOre extends Block {

	public BlockCorbamiteOre(Material material) {
		super(material);
		
		
		this.setBlockName("BlockCorbamiteOre");		
		this.setCreativeTab(fuelcraft.tabFuelcraft);
		this.setBlockTextureName("fc:BlockCorbamiteOre");
		this.setHardness(0.5F);
		this.setLightLevel(0.2F);
		
	}
	
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune) {
		return fcItems.Corbamite;
	}

	@Override
	public int quantityDropped(Random rand) {
		return 3 + rand.nextInt(3);
	}
	
}
