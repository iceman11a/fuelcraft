package iceman11a.fuelcraft.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiDieselTrainEngine extends GuiScreen
{
    public GuiDieselTrainEngine()
    {
        super();
    }

    @SuppressWarnings("unchecked")
    public void initGui()
    {
        this.buttonList.add(new GuiButton(0, 10, 25, 100, 20, "Start Engine"));
        this.buttonList.add(new GuiButton(1, 10, 50, 100, 20, "Slow Speed"));
        this.buttonList.add(new GuiButton(2, 10, 75, 100, 20, "1/2 Speed"));
        this.buttonList.add(new GuiButton(3, 10, 100, 100, 20, "Full Speed"));
        this.buttonList.add(new GuiButton(4, 10, 125, 100, 20, "Shutdown"));
    }

    public void actionPreformed(GuiButton button)
    {
        switch(button.id)
        {
            case 0:
                Minecraft.getMinecraft().thePlayer.sendChatMessage("Engine Started");
                //Start the diesel engine here
                //Sound for engine idle.
                //Generate heat for progressbar and any thing else.
                break;
            case 1:
                //Runs in slow speed
                //Sound for engine running
                break;
            case 2:
                //Runs in 1/2 speed
                //Sound for engine running
                break;
            case 3:
                //runs at full speed
                //Sound for engine running
                break;
            case 4:
                //Shutdown engine
                //Turn Sound off for engine
                break;
            default:
        }
    }
}
