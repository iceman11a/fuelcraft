package iceman11a.fuelcraft.inventory;

import iceman11a.fuelcraft.tileentity.TileEntityTapoilProducer;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerTapoilProducer extends ContainerFluidProcessor
{
    public ContainerTapoilProducer(InventoryPlayer inventoryPlayer, TileEntityTapoilProducer te)
    {
        super(inventoryPlayer, te);
        this.addPlayerInventorySlots(8, 94);
    }

    @Override
    protected void addCustomInventorySlots()
    {
        this.addSlotToContainer(new SlotGeneric(this.inventory, 0,   8, 55)); // Fuel
        this.addSlotToContainer(new SlotGeneric(this.inventory, 1,  34, 24)); // Input fluid buckets in
        this.addSlotToContainer(new SlotGeneric(this.inventory, 2,  34, 55)); // Input fluid buckets out
        this.addSlotToContainer(new SlotGeneric(this.inventory, 3, 152, 24)); // Output fluid buckets in
        this.addSlotToContainer(new SlotGeneric(this.inventory, 4, 152, 55)); // Output fluid buckets out
        this.addSlotToContainer(new SlotGeneric(this.inventory, 5,  77, 24)); // Saplings
        this.addSlotToContainer(new SlotGeneric(this.inventory, 6,  96, 24)); // Coal
    }
}
