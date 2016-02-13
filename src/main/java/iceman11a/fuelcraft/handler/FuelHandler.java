package iceman11a.fuelcraft.handler;

import iceman11a.fuelcraft.block.ore.BlockFuelcraftOre;
import iceman11a.fuelcraft.item.FuelcraftItems;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler
{
    @Override
    public int getBurnTime(ItemStack stack)
    {

        if(stack.getItem() == FuelcraftItems.resource)
        {
            int damage = stack.getItemDamage();

            switch (damage)
            {
                case BlockFuelcraftOre.META_CORBAMITE:
                    return 900;
                case BlockFuelcraftOre.META_CORCOAL:
                    return 400;
                case BlockFuelcraftOre.META_REDCOR:
                    return 1000;
                case BlockFuelcraftOre.META_BLACK_DIAMOND:
                    return 1200;
            }
        }

        return 0;
    }
}
