package org.id2k1149.dinerVoting.to;

import java.beans.ConstructorProperties;
import java.util.List;

public class DinerTo extends BaseTo {
    private final String title;
    private final List<MenuTo> menu;

    @ConstructorProperties({"id", "title", "menu"})
    public DinerTo(Long id,
                   String title,
                   List<MenuTo> menu) {
        super(id);
        this.title = title;
        this.menu = menu;
    }

    public String getTitle() {
        return title;
    }

    public List<MenuTo> getMenu() {
        return menu;
    }

    @Override
    public String toString() {
        return "DinerTo{" +
                "id = " + id +
                ", title =' " + title + '\'' +
                ", menu = " + menu +
                '}';
    }
}