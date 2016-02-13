package iceman11a.fuelcraft.gui;

import iceman11a.fuelcraft.inventory.ContainerFluidProcessor;
import iceman11a.fuelcraft.tileentity.TileEntityFluidProcessor;
import iceman11a.fuelcraft.tileentity.TileEntityTapoilProducer;
import iceman11a.fuelcraft.util.FuelcraftStringUtils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class GuiFluidProcessor extends GuiFuelCraftInventory
{
    protected ContainerFluidProcessor containerFluidProcessor;

    protected FluidStack fluidInput;
    protected FluidStack fluidOutput;

    protected int uFuelBackground = 192;
    protected int vFuelBackground = 0;

    protected int xFuelBurnIndicator = 26;
    protected int yFuelBurnIndicator = 55;
    protected int uFuelBurnIndicator = 193;
    protected int vFuelBurnIndicator = 68;

    protected int uFluidInputSlotBackground = 176;
    protected int vFluidInputSlotBackground = 0;

    protected int uFluidOutputSlotBackground = 176;
    protected int vFluidOutputSlotBackground = 0;

    protected int xEnergyMeter = 9;
    protected int yEnergyMeter = 14;
    protected int uEnergyMeter = 178;
    protected int vEnergyMeter = 69;

    protected int xInputTank = 55;
    protected int yInputTank = 24;
    protected int uInputTank = 209;
    protected int vInputTank = 17;

    protected int xOutputTank = 131;
    protected int yOutputTank = 24;
    protected int uOutputTank = 225;
    protected int vOutputTank = 17;

    protected int xTemperatureMeter = 116;
    protected int yTemperatureMeter = 57;
    protected int uTemperatureMeter = 186;
    protected int vTemperatureMeter = 68;

    protected int maxTemperature = TileEntityTapoilProducer.MAX_TEMPERATURE;
    protected int requiredTemperature = TileEntityTapoilProducer.REQUIRED_TEMPERATURE;

    public GuiFluidProcessor(ContainerFluidProcessor container, TileEntityFluidProcessor te, Fluid fluidInput, Fluid fluidOutput)
    {
        super(container, te);

        this.containerFluidProcessor = container;
        this.fluidInput = new FluidStack(fluidInput, 0);
        this.fluidOutput = new FluidStack(fluidOutput, 0);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float gameTicks, int mouseX, int mouseY)
    {
        super.drawGuiContainerBackgroundLayer(gameTicks, mouseX, mouseY);

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        Slot slot = this.inventorySlots.getSlot(TileEntityFluidProcessor.SLOT_FUEL);
        // Empty fuel input slot, draw the slot background
        if (slot.getStack() == null)
        {
            this.drawTexturedModalRect(x + slot.xDisplayPosition, y + slot.yDisplayPosition, this.uFuelBackground, this.vFuelBackground, 16, 16);
        }

        // Empty input fluid bucket (input) slot, draw the slot background
        slot = this.inventorySlots.getSlot(TileEntityFluidProcessor.SLOT_INPUT_FLUID_BUCKET_IN);
        if (slot.getStack() == null)
        {
            this.drawTexturedModalRect(x + slot.xDisplayPosition, y + slot.yDisplayPosition, this.uFluidInputSlotBackground, this.vFluidInputSlotBackground, 16, 16);
        }

        // Empty output fluid bucket (input) slot, draw the slot background
        slot = this.inventorySlots.getSlot(TileEntityFluidProcessor.SLOT_OUTPUT_FLUID_BUCKET_IN);
        if (slot.getStack() == null)
        {
            this.drawTexturedModalRect(x + slot.xDisplayPosition, y + slot.yDisplayPosition, this.uFluidOutputSlotBackground, this.vFluidOutputSlotBackground, 16, 16);
        }

        // Draw the burn time indicator
        if (this.containerFluidProcessor.burnTime > 0 && this.containerFluidProcessor.burnTimeFresh != 0)
        {
            int height = 16;
            int renderHeight = this.containerFluidProcessor.burnTime * height / this.containerFluidProcessor.burnTimeFresh;
            this.drawTexturedModalRect(x + this.xFuelBurnIndicator, y + this.yFuelBurnIndicator + height - renderHeight, this.uFuelBurnIndicator, this.vFuelBurnIndicator + height - renderHeight, 2, renderHeight);
        }

        // Draw the energy meter
        if (this.containerFluidProcessor.energyStored > 0)
        {
            int height = 36;
            int renderHeight = this.containerFluidProcessor.energyStored * height / TileEntityFluidProcessor.capacityEnergy;
            this.drawTexturedModalRect(x + this.xEnergyMeter, y + this.yEnergyMeter + height - renderHeight, this.uEnergyMeter, this.vEnergyMeter + height - renderHeight, 6, renderHeight);
        }

        // Draw the temperature gauge
        int height = 28;
        int renderHeight = height * this.containerFluidProcessor.temperature / this.maxTemperature;
        int yPos = height - renderHeight;
        this.drawTexturedModalRect(x + this.xTemperatureMeter, y + this.yTemperatureMeter + yPos, this.uTemperatureMeter, this.vTemperatureMeter + yPos, 5, renderHeight);

        // Draw the required-temperature marker
        yPos = height * (this.maxTemperature - this.requiredTemperature) / this.maxTemperature;
        this.drawTexturedModalRect(x + this.xTemperatureMeter, y + this.yTemperatureMeter + yPos, 196, 67, 12, 5);

        // Some input fluid stored in the tank -> draw the input fluid
        if (this.containerFluidProcessor.fluidAmountInput > 0 && this.fluidInput != null)
        {
            this.fluidInput.amount = this.containerFluidProcessor.fluidAmountInput;
            this.drawFluid(this.fluidInput, x + this.xInputTank, y + this.yInputTank, 14, 47, TileEntityFluidProcessor.capacityFluidInput);
        }

        // Draw the input tank gauge
        this.bindTexture(this.guiTexture);
        this.drawTexturedModalRect(x + this.xInputTank, y + this.yInputTank, this.uInputTank, this.vInputTank, 14, 47);

        // Some output fluid stored in the tank -> draw the output fluid
        if (this.containerFluidProcessor.fluidAmountOutput > 0 && this.fluidOutput != null)
        {
            this.fluidOutput.amount = this.containerFluidProcessor.fluidAmountOutput;
            this.drawFluid(this.fluidOutput, x + this.xOutputTank, y + this.yOutputTank, 14, 47, TileEntityFluidProcessor.capacityFluidOutput);
        }

        // Draw the output tank gauge
        this.bindTexture(this.guiTexture);
        this.drawTexturedModalRect(x + this.xOutputTank, y + this.yOutputTank, this.uOutputTank, this.vOutputTank, 14, 47);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        String str = this.te.hasCustomInventoryName() ? this.te.getInventoryName() : I18n.format(this.te.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(str, this.xSize / 2 - this.fontRendererObj.getStringWidth(str) / 2, 5, 0x404025);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, 84, 0x404025);
    }

    @Override
    protected void drawTooltips(int mouseX, int mouseY)
    {
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        List<String> list = new ArrayList<String>();
        // Hovering over the energy meter
        if (mouseX >= x + this.xEnergyMeter && mouseY >= y + this.yEnergyMeter && mouseX < x + this.xEnergyMeter + 5 && mouseY < y + this.yEnergyMeter + 36)
        {
            int energyAmount = this.containerFluidProcessor.energyStored;
            int energyCapacity = TileEntityFluidProcessor.capacityEnergy;
            list.add(FuelcraftStringUtils.formatNumberWithKSeparators(energyAmount) + " / " + FuelcraftStringUtils.formatNumberWithKSeparators(energyCapacity) + " RF");
            this.drawHoveringText(list, mouseX, mouseY, this.fontRendererObj);
        }
        // Hovering over the temperature meter
        else if (mouseX >= x + this.xTemperatureMeter && mouseY >= y + this.yTemperatureMeter && mouseX < x + this.xTemperatureMeter + 5 && mouseY < y + this.yTemperatureMeter + 28)
        {
            list.add(this.containerFluidProcessor.temperature + " \u00b0" + "C");
            this.drawHoveringText(list, mouseX, mouseY, this.fontRendererObj);
        }
        // Hovering over the input tank
        else if (mouseX >= x + this.xInputTank && mouseY >= y + this.yInputTank && mouseX < x + this.xInputTank + 14 && mouseY < y + this.yInputTank + 47)
        {
            int inputFluidAmount = this.containerFluidProcessor.fluidAmountInput;
            if (inputFluidAmount > 0)
            {
                if (this.fluidInput != null)
                {
                    list.add(this.fluidInput.getLocalizedName());
                }

                list.add(FuelcraftStringUtils.formatNumberWithKSeparators(inputFluidAmount) + " / " + FuelcraftStringUtils.formatNumberWithKSeparators(TileEntityFluidProcessor.capacityFluidInput) + " mB");
            }
            else
            {
                list.add(I18n.format("fuelcraft.gui.label.empty", new Object[0]));
            }

            this.drawHoveringText(list, mouseX, mouseY, this.fontRendererObj);
        }
        // Hovering over the output tank
        else if (mouseX >= x + this.xOutputTank && mouseY >= y + this.yOutputTank && mouseX < x + this.xOutputTank + 14 && mouseY < y + this.yOutputTank + 47)
        {
            int outputFluidAmount = this.containerFluidProcessor.fluidAmountOutput;
            if (outputFluidAmount > 0)
            {
                if (this.fluidOutput != null)
                {
                    list.add(this.fluidOutput.getLocalizedName());
                }

                list.add(FuelcraftStringUtils.formatNumberWithKSeparators(outputFluidAmount) + " / " + FuelcraftStringUtils.formatNumberWithKSeparators(TileEntityFluidProcessor.capacityFluidOutput) + " mB");
            }
            else
            {
                list.add(I18n.format("fuelcraft.gui.label.empty", new Object[0]));
            }

            this.drawHoveringText(list, mouseX, mouseY, this.fontRendererObj);
        }

        super.drawTooltips(mouseX, mouseY);
    }
}
