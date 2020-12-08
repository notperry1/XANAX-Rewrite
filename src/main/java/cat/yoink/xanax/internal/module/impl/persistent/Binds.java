package cat.yoink.xanax.internal.module.impl.persistent;

import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.ModuleManager;
import cat.yoink.xanax.internal.module.main.Module;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.stage.StateModule;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

@ModuleData(name = "Binds", category = ModuleCategory.CLIENT, hidden = true)
public final class Binds extends Module
{
    @SubscribeEvent
    public void onInputKeyInput(InputEvent.KeyInputEvent event)
    {
        if (Keyboard.getEventKeyState() && Keyboard.getEventKey() != Keyboard.KEY_NONE)
            ModuleManager.INSTANCE.getModules().stream()
                    .filter(module -> module.getBind() == Keyboard.getEventKey())
                    .forEach(StateModule::toggle);
    }
}

