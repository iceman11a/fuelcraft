package iceman11a.fuelcraft.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Custom logger to log all details from mod to help with peoples issues.
 */
public class GameLogger {

	public static GameLogger log = new GameLogger();

	static File fcFolder;
	static File fcLogFolder;
	static File configfcFolder;
	
	private static String dir = System.getenv("AppData") + "/.minecraft/";

	private static boolean configured;

	private Logger myLog;

	/**
	 * Configure the Tutorial logger
	 */
	private static void configureLogging() {
		log.myLog = Logger.getLogger("Fuelcarft-DIMENSION");
		configured = true;
	}

	public static void log(String targetLog, Level level, String msg, Object... data) {
		Logger.getLogger(targetLog).log(level, String.format(msg, data));
	}

	public static void log(Level level, String msg) {
		if (!configured) {
			configureLogging();
		}
		log.myLog.log(level, String.format(msg));
	}

	public static void log(String targetLog, Level level, Throwable ex, String msg, Object... data) {
		Logger.getLogger(targetLog).log(level, String.format(msg, data), ex);
	}

	public static void log(Level level, Throwable ex, String msg, Object... data) {
		if (!configured) {
			configureLogging();
		}
		log.myLog.log(level, String.format(msg, data), ex);
	}

	public static void severe(String msg, Object... data) {
		log(Level.SEVERE, msg);
	}

	public static void warning(String msg, Object... data) {
		log(Level.WARNING, msg);
	}

	/**
	 * 
	 * @param msg
	 */
	public static void info(String msg) {
		log(Level.INFO, msg);
	}

	/**
	 * Gets this Tutorial Logger.
	 * 
	 * @return
	 */
	public Logger getLogger() {
		return myLog;
	}

	/**
	 * Creates Tutorial folder.
	 * Creates Config folder in Tutorial folder.
	 */
	public static void createFolders() {
		fcFolder = new File(dir, "Tutorial");
		fcLogFolder = new File(dir + "fuelcraft/fcLog/");
		configfcFolder = new File(dir + "fuelcraft/Configs/");
		
		if (fcFolder.exists() != true || configfcFolder.exists() != true) {
			fcFolder.mkdirs();
			fcLogFolder.mkdirs();
			configfcFolder.mkdirs();
		}
	}

	/**
	 * Write text to the TutorialLog.log.
	 * 
	 * @param level
	 * @param text
	 */
	public static void writeToFile(Level level, String text) {
		File file;
		FileWriter writer;
		String newLine = System.getProperty("line.separator");
		try {
			file = new File(fcLogFolder, "Log.log");
			
			writer = new FileWriter(file, true);
			writer.write("[" + Details.MODID + "]" + "[" + level + "]" + " : " + text + newLine);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets Base file path
	 */
	public static String getFilePath(){
		return dir;
	}
}