package cat.yoink.xanax.internal.clickgui.buttons.settings;

import cat.yoink.xanax.internal.clickgui.buttons.SettingButton;
import cat.yoink.xanax.internal.font.CFontRenderer;
import cat.yoink.xanax.internal.module.ModuleManager;
import cat.yoink.xanax.internal.module.impl.toggleable.GuiModule;
import cat.yoink.xanax.internal.module.main.Module;
import cat.yoink.xanax.internal.setting.types.ListSetting;
import cat.yoink.xanax.internal.setting.types.StateSetting;
import cat.yoink.xanax.internal.util.GuiUtil;

import java.awt.*;

/**
 * @author yoink
 */
public final class ListButton extends SettingButton
{
    private final ListSetting setting;

    public ListButton(final Module module, final int x, final int y, final int w, final int h, final ListSetting setting)
    {
        super(module, x, y, w, h);
        this.setting = setting;
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final int windowX, final int windowY, final boolean self)
    {
        final boolean outline = ((StateSetting) ModuleManager.INSTANCE.getModule(GuiModule.class).getSetting("Outline")).getValue();

        final float[] hue = new float[]{(float) (System.currentTimeMillis() % 11520L) / 11520.0f};
        final Color c = new Color(Color.HSBtoRGB(hue[0], 1.0f, 1.0f));

        GuiUtil.drawRect(this.x + 5, this.y + 5, 50, 10, new Color(20, 20, 20).getRGB(), outline, c.getRGB());
        CFontRenderer.SMALLTEXT.drawString(this.setting.getValue(), this.x + 8, this.y + 6.5f, -1);

        CFontRenderer.TEXT.drawString(this.setting.getName(), this.x + 59, this.y + 5.5f, -1);
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton, final boolean self)
    {
        if (GuiUtil.isHover(this.x, this.y, this.w, this.h - 1, mouseX, mouseY))
        {
            if (mouseButton == 0) this.setting.cycleForward();
            else if (mouseButton == 1) this.setting.cycleBackward();
        }
    }

    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state)
    {

    }

    @Override
    public void keyTyped(final char typedChar, final int keyCode)
    {

    }

    @Override
    public void onGuiClosed()
    {

    }
}
