package cat.yoink.xanax.internal.module;

import cat.yoink.xanax.internal.module.impl.persistent.Binds;
import cat.yoink.xanax.internal.module.impl.toggleable.TestModule;
import cat.yoink.xanax.internal.module.main.Module;
import cat.yoink.xanax.internal.module.stage.StateModule;
import cat.yoink.xanax.internal.traits.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public enum ModuleManager implements Minecraft
{
    INSTANCE;

    ModuleManager()
    {
        addModules(new Binds(),
                new TestModule());
    }

    private final List<Module> allModules = new ArrayList<>();
    private final List<StateModule> modules = new ArrayList<>();

    private void addModules(Module... modules)
    {
        for (Module module : modules)
        {
            if (module instanceof StateModule) this.modules.add((StateModule) module);
            else MinecraftForge.EVENT_BUS.register(module);

            allModules.add(module);
        }

        getAllModules().sort(Comparator.comparing(module -> -mc.fontRenderer.getStringWidth(module.getName())));
    }

    public StateModule getStateModule(Class<? extends StateModule> name)
    {
        return modules.stream().filter(module -> module.getClass().equals(name)).findAny().orElse(null);
    }

    public List<StateModule> getModules()
    {
        return modules;
    }

    public List<Module> getAllModules()
    {
        return allModules;
    }
}
