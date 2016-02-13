package iceman11a.fuelcraft.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonTextCallback extends GuiButton
{
    protected final IButtonCallback callback;
    protected String[] strings;

    public GuiButtonTextCallback(int id, int x, int y, int w, int h, IButtonCallback callback, String[] strings)
    {
        super(id, x, y, w, h, "");
        this.callback = callback;
        this.strings = strings;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        this.displayString = this.strings[this.callback.getButtonState(this.id)];

        super.drawButton(mc, mouseX, mouseY);
    }
}
