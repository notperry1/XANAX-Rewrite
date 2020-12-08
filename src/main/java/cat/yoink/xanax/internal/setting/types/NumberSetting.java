package cat.yoink.xanax.internal.setting.types;

import cat.yoink.xanax.internal.setting.Setting;

import java.util.function.Predicate;

/**
 * @author yoink
 */
public final class NumberSetting extends Setting<Double>
{
    private final double minimum;
    private final double maximum;
    private final double increment;
    private double value;

    public NumberSetting(String name, double value, double minimum, double maximum, double increment)
    {
        super(name);
        this.minimum = minimum;
        this.maximum = maximum;
        this.increment = increment;
        this.value = value;
    }

    public NumberSetting(String name, Predicate<Setting<Double>> visible, double value, double minimum, double maximum, double increment)
    {
        super(name, visible);
        this.minimum = minimum;
        this.maximum = maximum;
        this.increment = increment;
        this.value = value;
    }

    @Override
    public Double getValue()
    {
        return value;
    }

    @Override
    public void setValue(Double value)
    {
        double precision = 1 / this.increment;
        this.value = Math.round(Math.max(this.minimum, Math.min(this.maximum, value)) * precision) / precision;
    }

    public double getMinimum()
    {
        return minimum;
    }

    public double getMaximum()
    {
        return maximum;
    }

    public double getIncrement()
    {
        return increment;
    }
}
