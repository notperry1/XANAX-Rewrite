package cat.yoink.xanax.internal.module.impl.toggleable;

import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.stage.StateModule;
import cat.yoink.xanax.internal.setting.types.StateSetting;
import org.lwjgl.input.Keyboard;

/**
 * @author yoink
 */
@ModuleData(name = "Test", category = ModuleCategory.MISC, defaultBind = Keyboard.KEY_R)
public final class TestModule extends StateModule
{
    private final StateSetting test = addSetting(new StateSetting("Test", true));

    @Override
    public void onEnable()
    {
        System.out.println(getName() + " enabled");
    }

    @Override
    public void onDisable()
    {
        System.out.println(getName() + " disabled");
    }
}
