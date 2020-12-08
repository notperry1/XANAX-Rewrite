package cat.yoink.xanax.internal.module.stage;

import cat.yoink.xanax.internal.module.main.Module;
import cat.yoink.xanax.internal.module.main.ModuleData;
import net.minecraftforge.common.MinecraftForge;

public abstract class StateModule extends Module
{
    protected boolean enabled = getClass().getAnnotation(ModuleData.class).enabled();

    public final void toggle()
    {
        setEnabled(!isEnabled());
    }

    public final boolean isEnabled()
    {
        return enabled;
    }

    public final void setEnabled(boolean enabled)
    {
        if (enabled)
        {
            onEnable();
            MinecraftForge.EVENT_BUS.register(this);
        }
        else
        {
            onDisable();
            MinecraftForge.EVENT_BUS.unregister(this);
        }
        this.enabled = enabled;
    }

    public void onEnable() { }
    public void onDisable() { }
}
