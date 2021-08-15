package org.id2k1149.project_v10.to;

import java.beans.ConstructorProperties;
import java.util.List;

public class AnswerTo extends BaseTo {
    private final String title;
    private final List<InfoTo> info;

    @ConstructorProperties({"id", "info"})
    public AnswerTo(Long id,
                    String title,
                    List<InfoTo> info) {
        super(id);
        this.title = title;
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public List<InfoTo> getInfoTo() {
        return info;
    }

    @Override
    public String toString() {
        return "AnswerTo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", info=" + info +
                '}';
    }
}