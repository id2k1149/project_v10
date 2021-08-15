package org.id2k1149.project_v10.util;

import org.id2k1149.project_v10.model.Answer;
import org.id2k1149.project_v10.model.Info;
import org.id2k1149.project_v10.to.AnswerTo;
import org.id2k1149.project_v10.to.InfoTo;
import java.util.ArrayList;
import java.util.List;

public class AnswerUtil {
    public static List<AnswerTo> getAnswersTo(List<Answer> answers, List<Info> infoList) {

        List<AnswerTo> answerToList = new ArrayList<>();
        answers.forEach(answer -> {
            List<InfoTo> infoToList = InfoUtil.getInfoTo(answer, infoList);
            answerToList.add(new AnswerTo(answer.getId(), answer.getTitle(), infoToList));
        });
        return answerToList;
    }
}