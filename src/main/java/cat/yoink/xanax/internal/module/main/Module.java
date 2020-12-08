package cat.yoink.xanax.internal.module.main;

import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.traits.Minecraft;
import cat.yoink.xanax.internal.traits.Nameable;

public abstract class Module implements Minecraft, Nameable, IModule
{
    protected final String name = getClass().getAnnotation(ModuleData.class).name();
    protected final ModuleCategory category = getClass().getAnnotation(ModuleData.class).category();
    protected final boolean hidden = getClass().getAnnotation(ModuleData.class).hidden();
    protected int bind = getClass().getAnnotation(ModuleData.class).defaultBind();

    protected final boolean isSafe()
    {
        return mc.player != null && mc.world != null;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public ModuleCategory getCategory()
    {
        return category;
    }

    @Override
    public int getBind()
    {
        return bind;
    }

    @Override
    public void setBind(int bind)
    {
        this.bind = bind;
    }

    @Override
    public boolean isHidden()
    {
        return hidden;
    }
}
