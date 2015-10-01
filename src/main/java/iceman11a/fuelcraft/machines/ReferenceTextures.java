package iceman11a.fuelcraft.machines;

import net.minecraft.util.ResourceLocation;
import iceman11a.fuelcraft.Util.Details;
import iceman11a.fuelcraft.machines.ResourceLocationHelper;

public class ReferenceTextures {
	
	   public static final String RESOURCE_PREFIX = Details.MID.toLowerCase() + ":";
	    public static final String GUI_SHEET_LOCATION = "textures/gui/";
	    public static final String ITEM_SHEET_LOCATION = "textures/items/";
	    public static final String MODEL_TEXTURE_LOCATION = "textures/models/";


	    public static ResourceLocation getGuiTexture(String name)
	    {
	        return ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + name + ".png");
	    }

	    public static String getItemTextureName(String name)
	    {
	        return Details.MID + ":" + name;
	    }

	    public static String getEntityTextureName(String name)
	    {
	        return Details.MID + ":textures/entity/entity." + name + ".png";
	    }

	    public static String getTileName(String name)
	    {
	        return Details.MID + ":" + name;
	    }

	    public static String getSlotBackgroundName(String itemName)
	    {
	        return Details.MID + ":gui/gui.slot.background." + itemName;
	    }

}
