package iceman11a.fuelcraft.reference;

import net.minecraft.util.ResourceLocation;

public class ReferenceTextures
{
    public static final String RESOURCE_PREFIX = Reference.MOD_ID.toLowerCase() + ":";
    public static final String GUI_SHEET_LOCATION = "textures/gui/";
    public static final String ITEM_SHEET_LOCATION = "textures/items/";
    public static final String MODEL_TEXTURE_LOCATION = "textures/models/";


    public static ResourceLocation getGuiTexture(String name)
    {
        return ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + name + ".png");
    }

    public static String getTileName(String name)
    {
        return Reference.MOD_ID + ":" + name;
    }

    public static String getFluidName(String name)
    {
        return Reference.MOD_ID + ":fluid." + name;
    }

    public static String getItemTextureName(String name)
    {
        return Reference.MOD_ID + ":" + name;
    }

    public static String getEntityTextureName(String name)
    {
        return Reference.MOD_ID + ":textures/entity/entity." + name + ".png";
    }

    public static String getSlotBackgroundName(String itemName)
    {
        return Reference.MOD_ID + ":gui/gui.slot.background." + itemName;
    }
}
