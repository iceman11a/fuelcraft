package iceman11a.fuelcraft.inventory;

import iceman11a.fuelcraft.tileentity.TileEntityFluidProcessor;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerFluidProcessor extends ContainerTileEntityInventory
{
    private TileEntityFluidProcessor tileEntityFluidProcessor;
    public int fluidAmountInput;
    public int fluidAmountOutput;
    public int energyStored;
    public int burnTime;
    public int burnTimeFresh;
    public int temperature;

    public ContainerFluidProcessor(InventoryPlayer inventoryPlayer, TileEntityFluidProcessor te)
    {
        super(inventoryPlayer, te);
        this.tileEntityFluidProcessor = te;
        this.addPlayerInventorySlots(8, 94);
    }

    @Override
    protected void addCustomInventorySlots()
    {
        this.addSlotToContainer(new SlotGeneric(this.inventory, 0,   8, 55));
        this.addSlotToContainer(new SlotGeneric(this.inventory, 1,  34, 24));
        this.addSlotToContainer(new SlotGeneric(this.inventory, 2,  34, 55));
        this.addSlotToContainer(new SlotGeneric(this.inventory, 3, 152, 24));
        this.addSlotToContainer(new SlotGeneric(this.inventory, 4, 152, 55));
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            // The values need to fit into a short, where these get truncated to in non-local SMP

            if (this.energyStored != this.tileEntityFluidProcessor.getEnergyStored(ForgeDirection.UP))
            {
                this.energyStored = this.tileEntityFluidProcessor.getEnergyStored(ForgeDirection.UP);
                icrafting.sendProgressBarUpdate(this, 0, this.energyStored / 10);
            }

            if (this.fluidAmountInput != this.tileEntityFluidProcessor.getInputFluidAmount())
            {
                this.fluidAmountInput = this.tileEntityFluidProcessor.getInputFluidAmount();
                icrafting.sendProgressBarUpdate(this, 1, this.fluidAmountInput);
            }

            if (this.fluidAmountOutput != this.tileEntityFluidProcessor.getOutputFluidAmount())
            {
                this.fluidAmountOutput = this.tileEntityFluidProcessor.getOutputFluidAmount();
                icrafting.sendProgressBarUpdate(this, 2, this.fluidAmountOutput);
            }

            if (this.burnTime != this.tileEntityFluidProcessor.getFuelBurnTime())
            {
                this.burnTime = this.tileEntityFluidProcessor.getFuelBurnTime();
                icrafting.sendProgressBarUpdate(this, 3, this.burnTime / 10);
            }

            if (this.burnTimeFresh != this.tileEntityFluidProcessor.getFuelBurnTimeFresh())
            {
                this.burnTimeFresh = this.tileEntityFluidProcessor.getFuelBurnTimeFresh();
                icrafting.sendProgressBarUpdate(this, 4, this.tileEntityFluidProcessor.getFuelBurnTimeFresh() / 10);
            }

            if (this.temperature != this.tileEntityFluidProcessor.getTemperature())
            {
                this.temperature = (int)this.tileEntityFluidProcessor.getTemperature();
                icrafting.sendProgressBarUpdate(this, 5, this.temperature);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int var, int val)
    {
        switch (var)
        {
            case 0:
                this.energyStored = val * 10;
                break;
            case 1:
                this.fluidAmountInput = val;
                break;
            case 2:
                this.fluidAmountOutput = val;
                break;
            case 3:
                this.burnTime = val * 10;
                break;
            case 4:
                this.burnTimeFresh = val * 10;
                break;
            case 5:
                this.temperature = val;
                break;
            default:
        }
    }
}
