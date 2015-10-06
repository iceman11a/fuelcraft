package iceman11a.fuelcraft.block.fcblock;

import java.util.Random;

import iceman11a.fuelcraft.fuelcraft;
import iceman11a.fuelcraft.fcItems.fcItems;
import iceman11a.fuelcraft.machines.ReferenceNames;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockCorCoalOre extends Block {
	
	public String blockName;

	protected BlockCorCoalOre(Material material) {
				
		super(material);        	
		this.setBlockName("BlockCorCoalOre");		
		this.setCreativeTab(fuelcraft.tabFuelcraft);
		this.setBlockTextureName("fc:BlockCorCoalOre");
		this.setHardness(0.5F);
		this.setLightLevel(0.2F);
	}

	
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune) {
		return fcItems.CorCoal;
	}

	@Override
	public int quantityDropped(Random rand) {
		return 3 + rand.nextInt(3);
	}
}
