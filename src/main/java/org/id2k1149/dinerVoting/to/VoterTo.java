package org.id2k1149.dinerVoting.to;

import org.id2k1149.dinerVoting.model.Diner;

import java.beans.ConstructorProperties;
import java.time.LocalDate;

public class VoterTo extends BaseTo {
    private final LocalDate date;
    private final Diner diner;

    @ConstructorProperties({"id", "date", "diner"})
    public VoterTo(Long id,
                   LocalDate date,
                   Diner diner) {
        super(id);
        this.date = date;
        this.diner = diner;
    }

    public LocalDate getDate() {
        return date;
    }

    public Diner getDiner() {
        return diner;
    }

    @Override
    public String toString() {
        return "VoterTo{" +
                "date = " + date +
                ", diner = " + diner +
                '}';
    }
}
