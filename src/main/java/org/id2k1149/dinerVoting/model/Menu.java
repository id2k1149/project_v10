package org.id2k1149.dinerVoting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Menu {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private LocalDate date = LocalDate.now();
    @ManyToOne
    private Diner diner;
    @ElementCollection
    private Map<String, BigDecimal> dishAndPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Menu)) return false;
        Menu menu = (Menu) o;
        return getId().equals(menu.getId())
                && getDate().equals(menu.getDate())
                && getDiner().equals(menu.getDiner())
                && getDishAndPrice().equals(menu.getDishAndPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getDate(),
                getDiner(),
                getDishAndPrice());
    }
}