package cat.yoink.xanax.internal.module.main;

import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.setting.Setting;
import cat.yoink.xanax.internal.traits.Minecraft;
import cat.yoink.xanax.internal.traits.Nameable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yoink
 */
public abstract class Module implements Minecraft, Nameable, IModule
{
    protected final String name = getClass().getAnnotation(ModuleData.class).name();
    protected final ModuleCategory category = getClass().getAnnotation(ModuleData.class).category();
    protected final boolean hidden = getClass().getAnnotation(ModuleData.class).hidden();
    protected int bind = getClass().getAnnotation(ModuleData.class).defaultBind();
    protected final List<Setting<?>> settings = new ArrayList<>();

    protected final boolean isSafe()
    {
        return mc.player != null && mc.world != null;
    }

    protected final <T extends Setting<?>> T addSetting(T setting)
    {
        settings.add(setting);
        return setting;
    }

    @Override
    public final String getName()
    {
        return name;
    }

    @Override
    public final ModuleCategory getCategory()
    {
        return category;
    }

    @Override
    public final int getBind()
    {
        return bind;
    }

    @Override
    public final void setBind(int bind)
    {
        this.bind = bind;
    }

    @Override
    public final boolean isHidden()
    {
        return hidden;
    }

    @Override
    public final List<Setting<?>> getSettings()
    {
        return settings;
    }
}
