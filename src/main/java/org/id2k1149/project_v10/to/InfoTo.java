package org.id2k1149.project_v10.to;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class InfoTo extends BaseTo {
    private final LocalDate date;
    private final Map<String, BigDecimal> details;

    @ConstructorProperties({"id", "date", "details"})
    public InfoTo(Long id,
                  LocalDate date,
                  Map<String, BigDecimal> details) {
        super(id);
        this.date = date;
        this.details = details;
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<String, BigDecimal> getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "InfoTo{" +
                "date=" + date +
                ", details=" + details +
                '}';
    }
}