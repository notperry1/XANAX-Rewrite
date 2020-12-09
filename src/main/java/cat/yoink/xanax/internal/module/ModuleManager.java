package cat.yoink.xanax.internal.module;

import cat.yoink.xanax.internal.module.impl.persistent.Binds;
import cat.yoink.xanax.internal.module.impl.persistent.Commands;
import cat.yoink.xanax.internal.module.impl.toggleable.client.GuiModule;
import cat.yoink.xanax.internal.module.impl.toggleable.combat.Velocity;
import cat.yoink.xanax.internal.module.main.Module;
import cat.yoink.xanax.internal.module.state.StateModule;
import cat.yoink.xanax.internal.setting.types.ListSetting;
import cat.yoink.xanax.internal.setting.types.NumberSetting;
import cat.yoink.xanax.internal.setting.types.StateSetting;
import cat.yoink.xanax.internal.traits.Configurable;
import cat.yoink.xanax.internal.traits.Minecraft;
import cat.yoink.xanax.internal.util.FileUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author yoink
 */
public enum ModuleManager implements Configurable, Minecraft
{
    INSTANCE;

    private final List<Module> allModules = new ArrayList<>();
    private final List<StateModule> modules = new ArrayList<>();

    ModuleManager()
    {
        addModules(new Binds(),
                new GuiModule(),
                new Commands(),
                new Velocity());
    }

    @Override
    public void save()
    {
        JsonObject config = new JsonObject();

        allModules.forEach(module -> {
            JsonObject mod = new JsonObject();

            JsonObject settings = new JsonObject();

            module.getSettings().forEach(setting -> {
                if (setting instanceof StateSetting) settings.addProperty(setting.getName(), ((StateSetting) setting).getValue());
                else if (setting instanceof NumberSetting) settings.addProperty(setting.getName(), ((NumberSetting) setting).getValue());
                else if (setting instanceof ListSetting) settings.addProperty(setting.getName(), ((ListSetting) setting).getValue());
            });

            mod.addProperty("state", !(module instanceof StateModule) || ((StateModule) module).isEnabled());
            mod.add("settings", settings);

            config.add(module.getName(), mod);
        });

        FileUtil.saveFile(new File(directory.getAbsolutePath(), "Modules.json"), config.toString());
    }

    @Override
    public void load()
    {
        JsonObject json = new JsonParser().parse(FileUtil.getContents(new File(directory.getAbsolutePath(), "Modules.json"))).getAsJsonObject();

        allModules.forEach(module -> {
            JsonObject jsonModule = json.get(module.getName()).getAsJsonObject();

            if (module instanceof StateModule) ((StateModule) module).setEnabled(jsonModule.get("state").getAsBoolean());

            JsonObject settings = jsonModule.get("settings").getAsJsonObject();

            module.getSettings().forEach(setting -> {
                JsonElement element = settings.get(setting.getName());

                if (setting instanceof StateSetting) ((StateSetting) setting).setValue(element.getAsBoolean());
                else if (setting instanceof NumberSetting) ((NumberSetting) setting).setValue(element.getAsDouble());
                else if (setting instanceof ListSetting) ((ListSetting) setting).setValue(element.getAsString());
            });
        });
    }

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

    public Module getModule(Class<? extends Module> name)
    {
        return allModules.stream().filter(module -> module.getClass().equals(name)).findAny().orElse(null);
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
