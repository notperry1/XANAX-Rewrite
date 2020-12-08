package cat.yoink.xanax.internal.module.main;

import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.setting.Setting;

import java.util.List;

public interface IModule
{
    ModuleCategory getCategory();

    int getBind();

    void setBind(int bind);

    boolean isHidden();

    List<Setting<?>> getSettings();
}
