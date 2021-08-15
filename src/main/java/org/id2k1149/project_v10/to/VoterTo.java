package org.id2k1149.project_v10.to;

import org.id2k1149.project_v10.model.Answer;

import java.beans.ConstructorProperties;
import java.time.LocalDate;

public class VoterTo extends BaseTo {
    private final LocalDate date;
    private final Answer answer;

    @ConstructorProperties({"id", "info"})
    public VoterTo(Long id,
                   LocalDate date,
                   Answer answer) {
        super(id);
        this.date = date;
        this.answer = answer;
    }

    public LocalDate getDate() {
        return date;
    }

    public Answer getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "VoterTo{" +
                "date=" + date +
                ", answer=" + answer +
                '}';
    }
}
