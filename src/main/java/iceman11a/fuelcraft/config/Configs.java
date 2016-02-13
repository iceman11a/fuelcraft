package iceman11a.fuelcraft.config;

import iceman11a.fuelcraft.Fuelcraft;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Configs
{
    public static Property propBiomeIdBlue;
    public static Property propBiomeIdDark;
    public static Property propBiomeIdLight;
    public static Property propDimensionIdLightForest;

    public static Property propTapoilProducerEnergyPerTapoilBucket;
    public static Property propTapoilProducerWaterPerTapoilBucket;
    public static Property propTapoilProducerSaplingValue;
    public static Property propTapoilProducerCoalValue;

    public static Property propOilProducerEnergyPerOilBucket;
    public static Property propOilProducerTapoilPerOilBucket;
    public static Property propOilProducerBlazepowderValue;

    public static Property propDieselProducerEnergyPerDieselBucket;
    public static Property propDieselProducerOilPerDieselBucket;

    public static int biomeIdBlue;
    public static int biomeIdDark;
    public static int biomeIdLight;
    public static int diomensionIdLightForest;

    public static int tapoilProducerEnergyPerTapoilBucket;
    public static int tapoilProducerWaterPerTapoilBucket;
    public static int tapoilProducerSaplingValue;
    public static int tapoilProducerCoalValue;

    public static int oilProducerEnergyPerOilBucket;
    public static int oilProducerTapoilPerOilBucket;
    public static int oilProducerBlazepowderValue;

    public static int dieselProducerEnergyPerDieselBucket;
    public static int dieselProducerOilPerDieselBucket;

    public static void loadConfigs(File configFile)
    {
        Fuelcraft.logger.info("Loading configuration...");

        Configuration conf = new Configuration(configFile, null, true); // true: Use case sensitive category names
        conf.load();

        String category = "BiomeIDs";
        propBiomeIdBlue = conf.get(category, "blueForestBiome", 52).setRequiresMcRestart(false);
        biomeIdBlue = propBiomeIdBlue.getInt();

        propBiomeIdDark = conf.get(category, "darkForestBiome", 51).setRequiresMcRestart(false);
        biomeIdDark = propBiomeIdDark.getInt();

        propBiomeIdLight = conf.get(category, "lightForestBiome", 50).setRequiresMcRestart(false);
        biomeIdLight = propBiomeIdLight.getInt();

        category = "DimensionIDs";
        propDimensionIdLightForest = conf.get(category, "lightDimension", 35).setRequiresMcRestart(false);
        diomensionIdLightForest = propDimensionIdLightForest.getInt();

        category = "Generic";

        // Tapoil Producer
        propTapoilProducerEnergyPerTapoilBucket = conf.get(category, "tapoilProducerEnergyPerTapoilBucket", 10000).setRequiresMcRestart(false);
        propTapoilProducerEnergyPerTapoilBucket.comment = "The amount of RF energy required to make one bucket of Tapoil. Default: 10000";
        tapoilProducerEnergyPerTapoilBucket = propTapoilProducerEnergyPerTapoilBucket.getInt();

        propTapoilProducerWaterPerTapoilBucket = conf.get(category, "tapoilProducerWaterPerTapoilBucket", 1000).setRequiresMcRestart(false);
        propTapoilProducerWaterPerTapoilBucket.comment = "The amount of Water required to make one bucket of Tapoil, in millibuckets. Default: 1000 (= 1 bucket)";
        tapoilProducerWaterPerTapoilBucket = propTapoilProducerWaterPerTapoilBucket.getInt();

        propTapoilProducerSaplingValue = conf.get(category, "tapoilProducerSaplingValue", 250).setRequiresMcRestart(false);
        propTapoilProducerSaplingValue.comment = "The amount of \"sapling mass\" in mB that you get per one sapling. One bucket of tapoil requires 1000 mB of \"sapling mass\". Default: 250 (= 4 saplings per 1 Bucket of tapoil)";
        tapoilProducerSaplingValue = propTapoilProducerSaplingValue.getInt();

        propTapoilProducerCoalValue = conf.get(category, "tapoilProducerCoalValue", 500).setRequiresMcRestart(false);
        propTapoilProducerCoalValue.comment = "The amount of \"coal mass\" in mB that you get per one coal. One bucket of tapoil requires 1000 mB of \"coal mass\". Default: 500 (= 2 coal per 1 bucket of tapoil)";
        tapoilProducerCoalValue = propTapoilProducerCoalValue.getInt();

        // Oil Producer
        propOilProducerEnergyPerOilBucket = conf.get(category, "oilProducerEnergyPerOilBucket", 100000).setRequiresMcRestart(false);
        propOilProducerEnergyPerOilBucket.comment = "The amount of RF energy required to make one bucket of Oil. Default: 100000";
        oilProducerEnergyPerOilBucket = propOilProducerEnergyPerOilBucket.getInt();

        propOilProducerTapoilPerOilBucket = conf.get(category, "oilProducerTapoilPerOilBucket", 2000).setRequiresMcRestart(false);
        propOilProducerTapoilPerOilBucket.comment = "The amount of Tapoil required to make one bucket of Oil, in millibuckets. Default: 2000 (= 2 buckets)";
        oilProducerTapoilPerOilBucket = propOilProducerTapoilPerOilBucket.getInt();

        propOilProducerBlazepowderValue = conf.get(category, "oilProducerBlazepowderValue", 500).setRequiresMcRestart(false);
        propOilProducerBlazepowderValue.comment = "The amount of \"blaze powder mass\" in mB that you get per one blaze powder. One bucket of oil requires 1000 mB of \"blaze powder mass\". Default: 500 (= 2 blaze powder per 1 bucket of oil)";
        oilProducerBlazepowderValue = propOilProducerBlazepowderValue.getInt();

        // Diesel Producer
        propDieselProducerEnergyPerDieselBucket = conf.get(category, "dieselProducerEnergyPerDieselBucket", 100000).setRequiresMcRestart(false);
        propDieselProducerEnergyPerDieselBucket.comment = "The amount of RF energy required to make one bucket of Diesel. Default: 100000";
        dieselProducerEnergyPerDieselBucket = propDieselProducerEnergyPerDieselBucket.getInt();

        propDieselProducerOilPerDieselBucket = conf.get(category, "dieselProducerOilPerDieselBucket", 4000).setRequiresMcRestart(false);
        propDieselProducerOilPerDieselBucket.comment = "The amount of Oil required to make one bucket of Diesel, in millibuckets. Default: 4000 (= 4 buckets)";
        dieselProducerOilPerDieselBucket = propDieselProducerOilPerDieselBucket.getInt();

        if (conf.hasChanged())
        {
            conf.save();
        }
    }
}
