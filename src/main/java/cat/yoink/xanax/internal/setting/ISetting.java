package cat.yoink.xanax.internal.setting;

public interface ISetting<T>
{
    T getValue();

    void setValue(T value);
}
