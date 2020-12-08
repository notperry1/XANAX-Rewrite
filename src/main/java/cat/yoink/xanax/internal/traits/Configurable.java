package cat.yoink.xanax.internal.traits;

import cat.yoink.xanax.internal.XANAX;

import java.io.File;

/**
 * @author yoink
 */
public interface Configurable
{
    File directory = new File(Minecraft.mc.gameDir + File.separator + XANAX.INSTANCE.getName());

    void save();

    void load();
}
