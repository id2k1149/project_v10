package org.id2k1149.dinerVoting.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.id2k1149.dinerVoting.model.Counter;
import org.id2k1149.dinerVoting.model.Voter;
import org.id2k1149.dinerVoting.service.CounterService;
import org.id2k1149.dinerVoting.service.MenuService;
import org.id2k1149.dinerVoting.service.VoterService;
import org.id2k1149.dinerVoting.to.DinerTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class VoteWebController {
    private final MenuService menuService;
    private final CounterService counterService;
    private final VoterService voterService;

    @GetMapping("/vote")
    public String survey(Model model) {
        List<DinerTo> dinersList = menuService.getTodayDinersList();
        if (dinersList.size() > 0) {
            if (!counterService.getTodayCounter()) {
                model.addAttribute("info3", "Can't create a new poll because there were votes today");
            }
            Optional<Voter> optionalVoter = voterService.getVoterByUserAndDate();
            if (optionalVoter.isPresent()) {
                model.addAttribute("info1", "you voted today...");
                if (LocalTime.now().getHour() > 11) {
                    model.addAttribute("info2", "It is too late to change your decision.");
                    log.info("It is too late to change decision.");
                    return "vote";
                }
            }
            model.addAttribute("dinersList", dinersList);
        } else {
            model.addAttribute("info2", "There are no polls today. Ask your Admin to Create a poll.");
            log.info("There are no polls today");
        }
        return "vote";
    }

    @PostMapping("/vote")
    public String vote(Counter counter) {
        if (counter.getDiner() == null) {
            log.error("wrong answer");
            return "redirect:/vote";
        }
        counterService.voteForDiner(counter.getDiner().getId());
        return "redirect:/result";
    }

    @GetMapping("/result")
    public String result(Model model) {
        List<Counter> sortedList = counterService.getTodayAllResults();
        if (sortedList.size() > 0) model.addAttribute("sortedList", sortedList);
        else model.addAttribute("error", "There are no results");
        return "result";
    }

    @GetMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String everyDayUpdate() {
        if (counterService.getTodayCounter()) menuService.everyDayUpdate(LocalDate.now());
        return "redirect:/vote";
    }
}