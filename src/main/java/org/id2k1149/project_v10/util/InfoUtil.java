package org.id2k1149.project_v10.util;

import org.id2k1149.project_v10.model.Answer;
import org.id2k1149.project_v10.model.Info;
import org.id2k1149.project_v10.to.InfoTo;

import java.util.List;
import java.util.stream.Collectors;

public class InfoUtil {
    public static List<InfoTo> getInfoTo(Answer answer, List<Info> infoList) {
        return infoList
                .stream()
                .filter(info -> info.getAnswer() == answer)
                .map(info -> new InfoTo(info.getId(), info.getDate(), info.getDetails()))
                .collect(Collectors.toList());
    }
}