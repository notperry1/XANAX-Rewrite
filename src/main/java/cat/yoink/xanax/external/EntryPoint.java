package cat.yoink.xanax.external;

import cat.yoink.xanax.internal.XANAX;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = "xanax", name = "XANAX")
public class EntryPoint
{
    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event)
    {
        XANAX.INSTANCE.start();
    }
}
