package iceman11a.fuelcraft.machines;

import net.minecraft.util.ResourceLocation;
import iceman11a.fuelcraft.Util.Details;
import iceman11a.fuelcraft.machines.Reference;

public class ResourceLocationHelper {
	
    public static ResourceLocation getResourceLocation(String modId, String path)
    {
        return new ResourceLocation(modId, path);
    }

    public static ResourceLocation getResourceLocation(String path)
    {
        return getResourceLocation(Details.MID.toLowerCase(), path);
    }

}
