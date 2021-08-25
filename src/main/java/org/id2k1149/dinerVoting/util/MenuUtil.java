package org.id2k1149.dinerVoting.util;

import org.id2k1149.dinerVoting.model.Diner;
import org.id2k1149.dinerVoting.model.Menu;
import org.id2k1149.dinerVoting.to.MenuTo;
import java.util.List;
import java.util.stream.Collectors;

public class MenuUtil {
    public static List<MenuTo> getMenuTo(Diner diner, List<Menu> menuList) {
        return menuList
                .stream()
                .filter(menu -> menu.getDiner() == diner)
                .map(menu -> new MenuTo(menu.getId(), menu.getDate(), menu.getDishPrice()))
                .collect(Collectors.toList());
    }
}