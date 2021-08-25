package org.id2k1149.dinerVoting.to;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class MenuTo extends BaseTo {
    private final LocalDate date;
    private final Map<String, BigDecimal> dishes;

    @ConstructorProperties({"id", "date", "details"})
    public MenuTo(Long id,
                  LocalDate date,
                  Map<String, BigDecimal> dishes) {
        super(id);
        this.date = date;
        this.dishes = dishes;
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<String, BigDecimal> getDishes() {
        return dishes;
    }

    @Override
    public String toString() {
        return "MenuTo{" +
                "date = " + date +
                ", dishes = " + dishes +
                '}';
    }
}