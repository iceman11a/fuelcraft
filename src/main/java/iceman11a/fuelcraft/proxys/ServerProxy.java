package iceman11a.fuelcraft.proxys;

import cpw.mods.fml.common.registry.GameRegistry;
import iceman11a.fuelcraft.machines.ReferenceNames;
import iceman11a.fuelcraft.machines.BlockDieselProducer;
import iceman11a.fuelcraft.tileentity.TileEntityDieselProducer;

public class ServerProxy{
	
	public void registerKeyBindings() { }
	public void registerRenderers() { }
	
	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityDieselProducer.class, ReferenceNames.getPrefixedName(ReferenceNames.NAME_TILE_ENTITY_DIESEL_PRODUCER));
	}	
}
