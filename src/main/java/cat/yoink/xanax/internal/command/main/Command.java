package cat.yoink.xanax.internal.command.main;

import cat.yoink.xanax.internal.traits.Minecraft;
import cat.yoink.xanax.internal.traits.Nameable;
import cat.yoink.xanax.internal.util.ChatUtil;

/**
 * @author yoink
 */
public abstract class Command implements Nameable, Minecraft
{
    protected final String name = getClass().getAnnotation(CommandData.class).name();
    protected final String[] aliases = getClass().getAnnotation(CommandData.class).aliases();
    protected final String usage = getClass().getAnnotation(CommandData.class).usage();
    protected final String description = getClass().getAnnotation(CommandData.class).description();

    protected final void printUsage()
    {
        ChatUtil.sendPrivateMessage("Usage: " + usage);
    }

    public abstract void run(String[] args);

    @Override
    public final String getName()
    {
        return name;
    }

    public final String[] getAliases()
    {
        return aliases;
    }

    public final String getUsage()
    {
        return usage;
    }

    public final String getDescription()
    {
        return description;
    }
}
