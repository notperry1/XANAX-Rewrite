package cat.yoink.xanax.internal.clickgui.buttons;

import cat.yoink.xanax.internal.clickgui.IGui;
import cat.yoink.xanax.internal.clickgui.buttons.settings.ListButton;
import cat.yoink.xanax.internal.clickgui.buttons.settings.NumberButton;
import cat.yoink.xanax.internal.clickgui.buttons.settings.StateButton;
import cat.yoink.xanax.internal.font.CFontRenderer;
import cat.yoink.xanax.internal.module.ModuleManager;
import cat.yoink.xanax.internal.module.impl.toggleable.GuiModule;
import cat.yoink.xanax.internal.module.main.Module;
import cat.yoink.xanax.internal.module.state.StateModule;
import cat.yoink.xanax.internal.setting.Setting;
import cat.yoink.xanax.internal.setting.types.ListSetting;
import cat.yoink.xanax.internal.setting.types.NumberSetting;
import cat.yoink.xanax.internal.setting.types.StateSetting;
import cat.yoink.xanax.internal.util.GuiUtil;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yoink
 */
public final class ModuleButton implements IGui
{
    private final List<SettingButton> buttons = new ArrayList<>();
    private final Module module;
    private final CategoryButton parent;
    private final int w;
    private final int h;
    private int x;
    private int y;
    private boolean selected;
    private int scroll;
    private boolean binding;

    public ModuleButton(final Module module, final int x, final int y, final int w, final int h, final CategoryButton parent, final int windowX, final int windowY)
    {
        this.module = module;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.parent = parent;

        int setI = 0;
        final List<Setting<?>> settings = module.getSettings();
        for (int i = 0; i < settings.size(); i++)
        {
            final Setting<?> setting = settings.get(i);
            final boolean left = i % 2 == 1;

            if (setting instanceof StateSetting)
                this.buttons.add(new StateButton(module, windowX + 15 + (left ? 175 : 0), windowY + 70 + setI * 20, 175, 20, (StateSetting) setting));
            else if (setting instanceof NumberSetting)
                this.buttons.add(new NumberButton(module, windowX + 15 + (left ? 175 : 0), windowY + 70 + setI * 20, 175, 20, (NumberSetting) setting));
            else if (setting instanceof ListSetting)
                this.buttons.add(new ListButton(module, windowX + 15 + (left ? 175 : 0), windowY + 70 + setI * 20, 175, 20, (ListSetting) setting));

            if (left) setI++;
        }
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final int windowX, final int windowY, final boolean self)
    {
        if (self)
        {
            if (this.selected)
            {
                final boolean outline = ((StateSetting) ModuleManager.INSTANCE.getStateModule(GuiModule.class).getSetting("Outline")).getValue();

                final float[] hue = new float[]{(float) (System.currentTimeMillis() % 11520L) / 11520.0f};
                final Color c = new Color(Color.HSBtoRGB(hue[0], 1.0f, 1.0f));

                if (outline) GuiUtil.drawSmoothRect(this.x - 1, this.y - 1, this.w + 2, this.h + 2, 2, c.getRGB());
                GuiUtil.drawSmoothRect(this.x, this.y, this.w, this.h + 3, 2, new Color(34, 34, 34).getRGB());
            }

            CFontRenderer.TEXT.drawCenteredString(this.binding ? "Bind..." : this.module.getName(), this.x + this.w / 2f, this.y + 3, module instanceof StateModule ? (((StateModule) this.module).isEnabled() ? -1 : new Color(150, 150, 150).getRGB()) : -1);
        }

        if (this.selected)
        {
            doScroll(windowX, windowY, mouseX, mouseY);

            int setI = 0;
            int setI2 = 0;
            for (int i = 0; i < this.buttons.size(); i++)
            {
                if (setI2 < this.scroll)
                {
                    setI2++;
                    continue;
                }

                if (setI >= 8) continue;

                final boolean left = i % 2 == 1;

                final SettingButton button = this.buttons.get(i);

                button.x = windowX + 15 + (left ? 175 : 0);
                button.y = windowY + 70 + setI * 20;
                button.drawScreen(mouseX, mouseY, windowX, windowY, true);

                if (left) setI++;
            }
        }
    }

    private void doScroll(final int windowX, final int windowY, final int mouseX, final int mouseY)
    {
        if (GuiUtil.isHover(windowX + 15, windowY + 70, 340, 160, mouseX, mouseY))
        {
            final int wheel = Mouse.getDWheel();
            if (wheel < 0 && this.scroll <= this.buttons.size() - 17) this.scroll += 2;
            else if (wheel > 0 && this.scroll > 0) this.scroll -= 2;
        }
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton, final boolean self)
    {
        if (GuiUtil.isHover(this.x, this.y, this.w, this.h, mouseX, mouseY) && self)
        {
            switch (mouseButton)
            {
                case 0:
                    if (module instanceof StateModule) ((StateModule) this.module).toggle();
                    break;
                case 1:
                    this.parent.getButtons().forEach(button -> button.selected = false);
                    this.selected = true;
                    break;
                case 2:
                    this.binding = !this.binding;
                    break;
                default:
                    break;
            }
        }

        if (this.selected) this.buttons.forEach(button -> button.mouseClicked(mouseX, mouseY, mouseButton, true));
    }

    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state)
    {
        if (this.selected) this.buttons.forEach(button -> button.mouseReleased(mouseX, mouseY, state));
    }

    @Override
    public void keyTyped(final char typedChar, final int keyCode)
    {
        if (this.binding)
        {
            if (keyCode == Keyboard.KEY_BACK || keyCode == Keyboard.KEY_DELETE) this.module.setBind(Keyboard.KEY_NONE);
            else this.module.setBind(keyCode);

            this.binding = false;
        }

        if (this.selected) this.buttons.forEach(button -> button.keyTyped(typedChar, keyCode));
    }

    @Override
    public void onGuiClosed()
    {
        if (this.selected) this.buttons.forEach(IGui::onGuiClosed);
    }

    public void setX(final int x)
    {
        this.x = x;
    }

    public void setY(final int y)
    {
        this.y = y;
    }
}
