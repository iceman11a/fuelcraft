package iceman11a.fuelcraft.block;

import cpw.mods.fml.common.registry.GameRegistry;
import iceman11a.fuelcraft.block.machine.BlockCrossingGate;
import iceman11a.fuelcraft.block.machine.BlockMachine;
import iceman11a.fuelcraft.block.ore.BlockFuelcraftOre;
import iceman11a.fuelcraft.reference.ReferenceNames;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class FuelcraftBlocks
{
    public static final Block effectLeaves = new BlockModEffectLeaves("effectLeaves", "effect");
    public static final BlockModFire lightFire = new BlockModFire("lightFire");
    public static final Block lightForestDirt = new BlockBasic(Material.rock, "lightForestDirt", "lightForestDirt", Block.soundTypeGrass);
    public static final Block lightForestGrass = new BlockModLightGrass(Material.rock, "lightForestGrass", Block.soundTypeGrass);
    public static final Block lightLeaves = new BlockModLightLeaves("lightLeaves", "light");
    public static final Block lightLog = new BlockModLightLog("lightLog", "log");
    public static final BlockModPortal lightPortal = new BlockModPortal("lightPortal");
    public static final Block lightSapling = new BlockModLightSapling("lightSapling", "light");
    public static final Block lightStone = new BlockBasic(Material.rock, "lightStone", "lightStone", Block.soundTypeStone);

    public static final Block blockOre = new BlockFuelcraftOre(ReferenceNames.NAME_TILE_ORES, 1.5f, Material.rock);
    public static final Block blockMachines = new BlockMachine(ReferenceNames.NAME_TILE_MACHINE, 1.0f, Material.iron);
    public static final Block blockCrossingGate = new BlockCrossingGate(ReferenceNames.NAME_TILE_CROSSING_GATE, 1.0f, Material.iron);
    public static final Block blockTokenTrack = new BlockTokenTrack(ReferenceNames.NAME_BLOCK_TOKEN_TRACK, 1.0f, Material.rock);

    /**
     * Register Blocks.
     */
    public static void registerBlocks()
    {
        // Terrain blocks
        GameRegistry.registerBlock(lightForestDirt, "lightForestDirt");
        GameRegistry.registerBlock(lightForestGrass, "lightForestGrass");
        GameRegistry.registerBlock(lightStone, "lightStone");
        GameRegistry.registerBlock(lightPortal, "lightPortal");
        GameRegistry.registerBlock(lightFire, "lightFire");
        GameRegistry.registerBlock(lightLog, "lightLog");
        GameRegistry.registerBlock(lightLeaves, "lightLeaves");
        GameRegistry.registerBlock(effectLeaves, "effectLeaves");
        GameRegistry.registerBlock(lightSapling, "lightSapling");

        // Tracks
        GameRegistry.registerBlock(blockTokenTrack, "blockTokenTrack");

        // Ores
        GameRegistry.registerBlock(blockOre, ItemBlockFuelcraftBase.class, ReferenceNames.NAME_TILE_ORES);

        // Machines
        GameRegistry.registerBlock(blockMachines, ItemBlockFuelcraftBase.class, ReferenceNames.NAME_TILE_MACHINE);

        GameRegistry.registerBlock(blockCrossingGate, ItemBlockFuelcraftBase.class, ReferenceNames.NAME_TILE_CROSSING_GATE);
    }
}
