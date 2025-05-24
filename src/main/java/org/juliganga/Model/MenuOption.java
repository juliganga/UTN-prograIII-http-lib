package org.juliganga.Model;

public class MenuOption {
    private String parent = null;
    private String option;
    final private Class type;


    public MenuOption(String parent, String option, Class type) {
        this.parent = parent;
        this.option = option;
        this.type = type;
    }

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
