package iceman11a.fuelcraft.util;

import iceman11a.fuelcraft.block.FuelcraftBlocks;
import iceman11a.fuelcraft.item.FuelcraftItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;


public class Recipies
{
    /**
     * Register Recipies with Game Registry.
     */
    public static void registerRecipies()
    {
        addShapelessRecipies();
        addShapedRecipies();
    }

    /**
     * Add Shaped Recpies.
     */
    private static void addShapedRecipies()
    {
        // Machines
        // 2 pcs of redstone, One pc of stone, and 3 pcs of glass
        GameRegistry.addRecipe(new ItemStack(FuelcraftBlocks.blockMachines, 1, 0), "R R", " S ", "GGG", 'R', new ItemStack(Items.redstone), 'S', new ItemStack(Blocks.stone), 'G', new ItemStack(Blocks.glass));

        // Items
        GameRegistry.addRecipe(new ItemStack(FuelcraftItems.resource, 4, 0), "C C", "   ", " S ", 'C', new ItemStack(Items.coal), 'S', new ItemStack(Blocks.stone));
    }

    /**
     * Add Shapless Recpies.
     */
    private static void addShapelessRecipies()
    {
        ItemStack dirt = new ItemStack(Blocks.dirt);
        ItemStack cobble = new ItemStack(Blocks.cobblestone);
        GameRegistry.addShapelessRecipe(new ItemStack(FuelcraftBlocks.lightStone, 1, 0), dirt, dirt, cobble, cobble);
    }
}