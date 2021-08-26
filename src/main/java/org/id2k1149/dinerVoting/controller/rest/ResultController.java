package org.id2k1149.dinerVoting.controller.rest;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.id2k1149.dinerVoting.model.Counter;
import org.id2k1149.dinerVoting.service.CounterService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = ResultController.REST_URL)
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
@RequiredArgsConstructor
public class ResultController {
    private final CounterService counterService;
    static final String REST_URL = "/api/v1/results";

    @GetMapping(path = "today")
    public List<Counter> getTodayAllResults() {
        return counterService.getTodayAllResults();
    }

    @GetMapping(path = "best")
    public Counter getTodayBestResult() {
        return counterService.getTodayBestResult();
    }

    @PostMapping(path = "{dinerId}")
    @ApiOperation(
            value = "Vote for diner with dinerId",
            notes = "Current user votes for diner with dinerId")
    public void voteForDiner(@PathVariable Long dinerId) {
        counterService.voteForDiner(dinerId);
    }
}
