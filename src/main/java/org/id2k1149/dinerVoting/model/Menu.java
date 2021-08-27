package org.id2k1149.dinerVoting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Menu {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private LocalDate date = LocalDate.now();
    @ManyToOne
    private Diner diner;
    @ElementCollection
    private Map<String, BigDecimal> dishAndPrice;
}