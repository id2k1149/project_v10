package org.id2k1149.dinerVoting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import java.util.Objects;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Diner {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @NotBlank
    private String title;
    public Diner(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Diner)) return false;
        Diner diner = (Diner) o;
        return getId().equals(diner.getId())
                && getTitle().equals(diner.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getTitle());
    }
}