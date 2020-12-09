package cat.yoink.xanax.internal.clickgui.buttons;

import cat.yoink.xanax.internal.clickgui.ClickGUI;
import cat.yoink.xanax.internal.clickgui.IGui;
import cat.yoink.xanax.internal.font.CFontRenderer;
import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.ModuleManager;
import cat.yoink.xanax.internal.module.impl.toggleable.GuiModule;
import cat.yoink.xanax.internal.module.main.Module;
import cat.yoink.xanax.internal.setting.types.StateSetting;
import cat.yoink.xanax.internal.traits.Minecraft;
import cat.yoink.xanax.internal.util.GuiUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yoink
 */
public final class CategoryButton implements IGui, Minecraft
{
    private final List<ModuleButton> buttons = new ArrayList<>();
    private final ModuleCategory category;
    private final int w;
    private final int h;
    private int x;
    private int y;
    private boolean selected;
    private int windowX, windowY;
    private int tab;

    public CategoryButton(ModuleCategory category, int x, int y, int w, int h, int windowX, int windowY)
    {
        this.category = category;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        final List<Module> modules = ModuleManager.INSTANCE.getAllModules().stream().filter(m -> m.getCategory().equals(category)).collect(Collectors.toList());
        for (int i = 0; i < modules.size(); i++)
        {
            this.buttons.add(new ModuleButton(modules.get(i), windowX + 30 + i * 65, y + 20, 60, 15, this, windowX, windowY));
        }
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final int windowX, final int windowY, final boolean self)
    {
        this.windowX = windowX;
        this.windowY = windowY;

        if (this.selected)
        {
            final boolean outline = ((StateSetting) ModuleManager.INSTANCE.getModule(GuiModule.class).getSetting("Outline")).getValue();

            final float[] hue = new float[]{(float) (System.currentTimeMillis() % 11520L) / 11520.0f};
            final Color c = new Color(Color.HSBtoRGB(hue[0], 1.0f, 1.0f));

            if (outline) GuiUtil.drawSmoothRect(this.x - 1, this.y - 1, this.w + 2, this.h + 2, 2, c.getRGB());
            GuiUtil.drawSmoothRect(this.x, this.y, this.w, this.h + 3, 2, new Color(43, 43, 43).getRGB());
        }

        CFontRenderer.TEXT.drawString(this.category.getName(), this.x + (this.w / 2f) - (CFontRenderer.TEXT.getStringWidth(this.category.getName()) / 2f), this.y + 3, -1);

        if (this.selected)
        {
            int modX = 0;
            for (int i = 0; i < this.buttons.size(); i++)
            {
                if (i < this.tab || i > this.tab + 4)
                {
                    this.buttons.get(i).drawScreen(mouseX, mouseY, windowX, windowY, false);
                    continue;
                }

                final ModuleButton button = this.buttons.get(i);

                button.setX(windowX + 30 + modX * 65);
                button.setY(this.y + 20);
                this.buttons.get(i).drawScreen(mouseX, mouseY, windowX, windowY, true);

                modX++;
            }
        }
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton, final boolean self)
    {
        if (GuiUtil.isHover(this.x, this.y, this.w, this.h, mouseX, mouseY) && mouseButton == 0)
        {
            ClickGUI.INSTANCE.getButtons().forEach(b -> b.selected = false);
            this.selected = true;
        }

        if (mouseButton == 0 && this.selected)
        {
            if (GuiUtil.isHover(this.windowX + 15, this.windowY + 57, 10, 10, mouseX, mouseY) && this.tab > 0)
                this.tab--;
            if (GuiUtil.isHover(this.windowX + 355, this.windowY + 57, 10, 10, mouseX, mouseY) && this.tab < this.buttons.size() - 5)
                this.tab++;
        }

        if (this.selected)
        {
            for (int i = 0; i < this.buttons.size(); i++)
            {
                if (i < this.tab || i > this.tab + 4)
                {
                    this.buttons.get(i).mouseClicked(mouseX, mouseY, mouseButton, false);
                    continue;
                }

                this.buttons.get(i).mouseClicked(mouseX, mouseY, mouseButton, true);
            }
        }
    }

    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state)
    {
        if (this.selected) this.buttons.forEach(button -> button.mouseReleased(mouseX, mouseY, state));
    }

    @Override
    public void keyTyped(final char typedChar, final int keyCode)
    {
        if (this.selected) this.buttons.forEach(button -> button.keyTyped(typedChar, keyCode));
    }

    @Override
    public void onGuiClosed()
    {
        if (this.selected) this.buttons.forEach(ModuleButton::onGuiClosed);
    }

    public void setX(final int x)
    {
        this.x = x;
    }

    public void setY(final int y)
    {
        this.y = y;
    }

    public List<ModuleButton> getButtons()
    {
        return this.buttons;
    }

    public boolean isSelected()
    {
        return this.selected;
    }

    public int getTab()
    {
        return this.tab;
    }
}
