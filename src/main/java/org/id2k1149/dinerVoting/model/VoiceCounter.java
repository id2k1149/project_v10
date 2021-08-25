package org.id2k1149.dinerVoting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VoiceCounter {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private LocalDate date = LocalDate.now();
    @ManyToOne
    private Diner diner;
    private Integer votes;
}