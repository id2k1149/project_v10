package org.id2k1149.project_v10.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Answer {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String title;

    public Answer(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}