package cat.yoink.xanax.internal.setting;

import cat.yoink.xanax.internal.traits.Nameable;

import java.util.function.Predicate;

public abstract class Setting<T> implements Nameable, ISetting<T>
{
    private final String name;
    private final Predicate<Setting<T>> visible;

    public Setting(String name)
    {
        this.name = name;
        this.visible = null;
    }

    public Setting(String name, Predicate<Setting<T>> visible)
    {
        this.name = name;
        this.visible = visible;
    }

    public final boolean isVisible()
    {
        if (visible == null) return true;
        return visible.test(this);
    }

    @Override
    public final String getName()
    {
        return name;
    }
}
