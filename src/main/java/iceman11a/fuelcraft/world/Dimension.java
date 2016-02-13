package iceman11a.fuelcraft.world;

import iceman11a.fuelcraft.config.Configs;
import net.minecraftforge.common.DimensionManager;

public class Dimension
{
    /**
     * Register dimensions.
     * @param register
     */
    public static void registerDimensions()
    {
        DimensionManager.registerDimension(Configs.diomensionIdLightForest, Configs.diomensionIdLightForest);
    }

    /**
     * Regster dimension world providers with the dimension manager.
     */
    public static void registerWorldProvider()
    {
        DimensionManager.registerProviderType(Configs.diomensionIdLightForest, WorldProviderForest.class, true);
    }
}
