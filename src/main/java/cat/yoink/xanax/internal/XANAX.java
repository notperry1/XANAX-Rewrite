package cat.yoink.xanax.internal;

import cat.yoink.xanax.internal.module.ModuleManager;
import cat.yoink.xanax.internal.traits.Nameable;

/**
 * @author yoink
 */
public enum XANAX implements Nameable
{
    INSTANCE;

    public void initialize()
    {
        ModuleManager instance = ModuleManager.INSTANCE;
        // Needed to initialize. Will be removed later.
    }

    @Override
    public String getName()
    {
        return "XANAX";
    }
}
