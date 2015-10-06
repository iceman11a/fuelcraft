package iceman11a.fuelcraft.worldgen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;
import cpw.mods.fml.common.IWorldGenerator;
import iceman11a.fuelcraft.block.fcblock.fcBlocks;


public class OreGeneration implements  IWorldGenerator {
		
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.dimensionId)
		{
		case -1:
			generateNether(world, random, chunkX * 16, chunkZ * 16);
			case 0:
			generateOverworld(world, random, chunkX * 16, chunkZ * 16);
			case 1:
			generateEnd(world, random, chunkX * 16, chunkZ * 16);
			case -99:
			generateLight(world, random, chunkX, chunkZ);
		
		}
	}

	public void generateEnd(World world, Random rand, int x, int z) {
		this.generateOre(fcBlocks.blockCorbamiteOre, world, rand, x, z, 16, 16, 6+rand.nextInt(16), 25, 38, 100);
	}
	
	public void generateOverworld(World world, Random rand, int x, int z) {
		this.generateOre(fcBlocks.blockCorbamiteOre, world, rand, x, z, 16, 16, 6+rand.nextInt(16), 25, 38, 100);
		
	}
	
	public void generateNether(World world, Random rand, int x, int z) {
		this.generateOre(fcBlocks.blockCorbamiteOre, world, rand, x, z, 16, 16, 6+rand.nextInt(16), 25, 38, 100);
	}
	
	public void generateLight(World world, Random rand, int x, int z) {
		this.generateOre(fcBlocks.blockCorbamiteOre, world, rand, x, z, 16, 16, 6+rand.nextInt(16), 25, 38, 100);
		this.generateOre(fcBlocks.blockCorCoalOre, world, rand, x, z, 16, 16, 6+rand.nextInt(16), 25, 38, 100);
	}
	
	public void generateOre(Block block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVienSize, int chanceToSpawn, int minY, int maxY) {
				
		for(int i = 0; i < chanceToSpawn; i++) {
			int posX = blockXPos + random.nextInt(maxX);
			int posY = minY + random.nextInt(maxY - minY);
			int posZ = blockZPos + random.nextInt(maxZ);
			 
			(new WorldGenMinable(block, maxVienSize)).generate(world, random, posX, posY, posZ); 
			
		}
	}	
}
