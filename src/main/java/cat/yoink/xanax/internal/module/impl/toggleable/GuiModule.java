package cat.yoink.xanax.internal.module.impl.toggleable;

import cat.yoink.xanax.internal.clickgui.ClickGUI;
import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.stage.StateModule;
import cat.yoink.xanax.internal.setting.types.ListSetting;
import cat.yoink.xanax.internal.setting.types.StateSetting;
import org.lwjgl.input.Keyboard;

/**
 * @author yoink
 */
@ModuleData(name = "ClickGUI", category = ModuleCategory.CLIENT, defaultBind = Keyboard.KEY_RSHIFT)
public final class GuiModule extends StateModule
{
    {
        addSetting(new StateSetting("Outline", false));
        addSetting(new ListSetting("Closing", "Keyboard", "Keyboard", "Button", "Both"));
    }

    @Override
    public void onEnable()
    {
        mc.displayGuiScreen(ClickGUI.INSTANCE);
    }
}
