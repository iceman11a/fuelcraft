package iceman11a.fuelcraft.config;

import iceman11a.fuelcraft.Fuelcraft;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Configs {
	public static Property propBiomeIdBlue;
	public static Property propBiomeIdDark;
	public static Property propBiomeIdLight;

	public static Property propDimensionIdLightForest;

	public static int biomeIdBlue;
	public static int biomeIdDark;
	public static int biomeIdLight;

	public static int diomensionIdLightForest;

	public static void loadConfigs(File configFile) {
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

		if (conf.hasChanged()) {
			conf.save();
		}
	}
}
