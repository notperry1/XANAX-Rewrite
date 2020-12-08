package cat.yoink.xanax.internal.clickgui.buttons;

import cat.yoink.xanax.internal.clickgui.IGui;
import cat.yoink.xanax.internal.module.main.Module;

/**
 * @author yoink
 */
public abstract class SettingButton implements IGui
{
    protected final Module module;
    protected final int w;
    protected final int h;
    protected int x;
    protected int y;

    public SettingButton(final Module module, final int x, final int y, final int w, final int h)
    {
        this.module = module;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
}
