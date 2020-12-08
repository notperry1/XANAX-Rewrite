package cat.yoink.xanax.internal.module.main;

import cat.yoink.xanax.internal.module.ModuleCategory;

public interface IModule
{
    ModuleCategory getCategory();

    int getBind();

    void setBind(int bind);

    boolean isHidden();
}
