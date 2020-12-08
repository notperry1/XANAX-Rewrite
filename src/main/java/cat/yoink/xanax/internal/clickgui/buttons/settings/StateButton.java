package cat.yoink.xanax.internal.clickgui.buttons.settings;

import cat.yoink.xanax.internal.clickgui.buttons.SettingButton;
import cat.yoink.xanax.internal.font.CFontRenderer;
import cat.yoink.xanax.internal.module.ModuleManager;
import cat.yoink.xanax.internal.module.impl.toggleable.GuiModule;
import cat.yoink.xanax.internal.module.main.Module;
import cat.yoink.xanax.internal.setting.types.StateSetting;
import cat.yoink.xanax.internal.util.GuiUtil;

import java.awt.*;

/**
 * @author yoink
 */
public final class StateButton extends SettingButton
{
    private final StateSetting setting;

    public StateButton(final Module module, final int x, final int y, final int w, final int h, final StateSetting setting)
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

        GuiUtil.drawSmoothRect(this.x + 5, this.y + 5, 10, 10, 1, new Color(20, 20, 20).getRGB(), outline, c.getRGB());

        if (this.setting.getValue()) GuiUtil.drawSmoothRect(this.x + 7, this.y + 7, 6, 6, 1, c.getRGB());

        CFontRenderer.TEXT.drawString(this.setting.getName(), this.x + 20, this.y + 5.5f, -1);
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton, final boolean self)
    {
        if (GuiUtil.isHover(this.x, this.y, this.w, this.h - 1, mouseX, mouseY)) this.setting.setValue(!setting.getValue());
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
