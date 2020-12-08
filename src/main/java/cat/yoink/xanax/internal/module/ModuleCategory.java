package cat.yoink.xanax.internal.module;

public enum ModuleCategory
{
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    MISC("Miscellaneous"),
    RENDER("Visuals"),
    EXPLOIT("Exploit"),
    CLIENT("Client");

    private final String name;

    ModuleCategory(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
