package iceman11a.fuelcraft.gui;

public interface IButtonCallback
{
    /**
     * Get the current value for button <b>buttonId</b>, that determines the state of the button.
     */
    int getButtonState(int buttonId);
}
