package cat.yoink.xanax.internal.module.impl.toggleable;

import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.stage.StateModule;
import org.lwjgl.input.Keyboard;

@ModuleData(name = "Test", category = ModuleCategory.MISC, defaultBind = Keyboard.KEY_R)
public final class TestModule extends StateModule
{
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
