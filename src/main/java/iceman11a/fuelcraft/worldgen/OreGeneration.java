package iceman11a.fuelcraft.worldgen;

import iceman11a.fuelcraft.block.FuelcraftBlocks;
import iceman11a.fuelcraft.block.ore.BlockFuelcraftOre;
import iceman11a.fuelcraft.config.Configs;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;


public class OreGeneration implements IWorldGenerator
{
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        switch(world.provider.dimensionId)
        {
            case 0:
                generateOverworld(world, random, chunkX * 16, chunkZ * 16);
                return;
            case -1:
                generateNether(world, random, chunkX * 16, chunkZ * 16);
                return;
            case 1:
                generateEnd(world, random, chunkX * 16, chunkZ * 16);
                return;
            default:
        }

        if (world.provider.dimensionId == Configs.diomensionIdLightForest)
        {
            generateLight(world, random, chunkX, chunkZ);
        }
    }

    public void generateEnd(World world, Random rand, int x, int z)
    {
        // Corbamite Ore
        this.generateOre(FuelcraftBlocks.blockOre, BlockFuelcraftOre.META_CORBAMITE, world, rand, x, z, 16, 16, 6 + rand.nextInt(16), 25, 38, 100);
    }

    public void generateOverworld(World world, Random rand, int x, int z)
    {
        // Corbamite Ore
        this.generateOre(FuelcraftBlocks.blockOre, BlockFuelcraftOre.META_CORBAMITE, world, rand, x, z, 16, 16, 6 + rand.nextInt(16), 25, 38, 100);
        // CorCoal Ore
        this.generateOre(FuelcraftBlocks.blockOre, BlockFuelcraftOre.META_CORCOAL, world, rand, x, z, 16, 16, 6 + rand.nextInt(16), 25, 38, 100);
    }

    public void generateNether(World world, Random rand, int x, int z)
    {
        // Corbamite Ore
        this.generateOre(FuelcraftBlocks.blockOre, BlockFuelcraftOre.META_CORBAMITE, world, rand, x, z, 16, 16, 6 + rand.nextInt(16), 25, 38, 100);
    }

    public void generateLight(World world, Random rand, int x, int z)
    {
        // Black Diamond Ore
        this.generateOre(FuelcraftBlocks.blockOre, BlockFuelcraftOre.META_BLACK_DIAMOND, world, rand, x, z, 16, 16, 6 + rand.nextInt(16), 25, 38, 100);
        // RedCor Ore
        this.generateOre(FuelcraftBlocks.blockOre, BlockFuelcraftOre.META_REDCOR, world, rand, x, z, 16, 16, 6 + rand.nextInt(16), 25, 38, 100);
        // CorCoal Ore
        this.generateOre(FuelcraftBlocks.blockOre, BlockFuelcraftOre.META_CORCOAL, world, rand, x, z, 16, 16, 6 + rand.nextInt(16), 25, 38, 100);
    }

    public void generateOre(Block block, int meta, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVeinSize, int chanceToSpawn, int minY, int maxY)
    {

        for(int i = 0; i < chanceToSpawn; i++)
        {
            int posX = blockXPos + random.nextInt(maxX);
            int posY = minY + random.nextInt(maxY - minY);
            int posZ = blockZPos + random.nextInt(maxZ);

            (new WorldGenMinable(block, meta, maxVeinSize, Blocks.stone)).generate(world, random, posX, posY, posZ);
        }
    }
}
