package org.id2k1149.project_v10.restController;

import lombok.RequiredArgsConstructor;
import org.id2k1149.project_v10.model.Answer;
import org.id2k1149.project_v10.service.AnswerService;
import org.id2k1149.project_v10.service.InfoService;
import org.id2k1149.project_v10.to.AnswerTo;
import org.id2k1149.project_v10.util.AnswerUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/answers")
@RequiredArgsConstructor
//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AnswerController {
    private final AnswerService answerService;
    private final InfoService infoService;

    @GetMapping
    public List<Answer> getAnswers() {
        return answerService.getAnswers();
    }

    @GetMapping(path = "{id}")
    public Answer getAnswer(@PathVariable Long id) {
        return answerService.getAnswer(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addAnswer(@RequestBody Answer newAnswer) {
        answerService.addAnswer(newAnswer);
    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAnswer(
            @RequestBody Answer answer,
            @PathVariable Long id
    ) {
        answerService.updateAnswer(answer, id);
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnswer(@PathVariable Long id) {
        answerService.deleteAnswer(id);
    }

    @GetMapping("/today")
    public List<AnswerTo> getTodayInfo() {
        return AnswerUtil.getAnswersTo(infoService.getTodayAnswersInfo(), infoService.getByDate(LocalDate.now()));
    }

    @GetMapping(path = "{id}/info")
    public AnswerTo getAllInfoForAnswer(@PathVariable Long id) {
        return answerService.getAllInfoForAnswer(id);
    }
}