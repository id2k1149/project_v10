package org.id2k1149.dinerVoting.util;

import org.id2k1149.dinerVoting.model.Diner;
import org.id2k1149.dinerVoting.model.Menu;
import org.id2k1149.dinerVoting.to.DinerTo;
import org.id2k1149.dinerVoting.to.MenuTo;

import java.util.ArrayList;
import java.util.List;

public class DinerUtil {
    public static List<DinerTo> getDinersTo(List<Diner> diners, List<Menu> menuList) {

        List<DinerTo> dinerToList = new ArrayList<>();
        diners.forEach(diner -> {
            List<MenuTo> menuToList = MenuUtil.getMenuTo(diner, menuList);
            dinerToList.add(new DinerTo(diner.getId(), diner.getTitle(), menuToList));
        });
        return dinerToList;
    }
}