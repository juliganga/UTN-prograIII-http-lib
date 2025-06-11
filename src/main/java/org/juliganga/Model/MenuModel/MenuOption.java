package org.juliganga.Model.MenuModel;

/** <h1>Esto está abandonado.</h1>
 *
 * @deprecated
 * Esto está abandonado. No se hará un frontend en este programa.
 *
 * */
@Deprecated
public class MenuOption {
    private String parent = null;
    private String option;
    final private Class type;


    @Deprecated
    public MenuOption(String parent, String option, Class type) {
        this.parent = parent;
        this.option = option;
        this.type = type;
    }

    @Deprecated
    public MenuOption(String option, Class type) {
        this.option = option;
        this.type = type;
    }

    public String getParent() {
        return parent;
    }

    public String getOption() {
        return option;
    }

    public Class getType() {
        return type;
    }

    @Override
    public String toString() {
        return "MenuOption{" +
                "parent='" + parent + '\'' +
                ", option='" + option + '\'' +
                ", type=" + type +
                '}';
    }
}
