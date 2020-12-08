package cat.yoink.xanax.internal.setting.types;

import cat.yoink.xanax.internal.setting.Setting;

import java.util.function.Predicate;

public final class StateSetting extends Setting<Boolean>
{
    private boolean value;

    public StateSetting(String name, boolean value)
    {
        super(name);
        this.value = value;
    }

    public StateSetting(String name, Predicate<Setting<Boolean>> visible, boolean value)
    {
        super(name, visible);
        this.value = value;
    }

    @Override
    public Boolean getValue()
    {
        return value;
    }

    @Override
    public void setValue(Boolean value)
    {
        this.value = value;
    }
}
