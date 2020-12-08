package cat.yoink.xanax.internal.setting;

/**
 * @author yoink
 */
public interface ISetting<T>
{
    T getValue();

    void setValue(T value);
}
