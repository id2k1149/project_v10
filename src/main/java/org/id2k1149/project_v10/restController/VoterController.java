package org.id2k1149.project_v10.restController;

import lombok.RequiredArgsConstructor;
import org.id2k1149.project_v10.model.Voter;
import org.id2k1149.project_v10.service.VoterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/voters")
@RequiredArgsConstructor
public class VoterController {
    private final VoterService voterService;

    @GetMapping
    public List<Voter> getVoters() {
        return voterService.getVoters();
    }

    @GetMapping(path = "{id}")
    public Voter getVoter(@PathVariable Long id) {
        return voterService.getVoter(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addVoter(@RequestBody Voter newVoter) {
        voterService.addVoter(newVoter);
    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVoter(
            @RequestBody Voter voter,
            @PathVariable Long id
    ) {
        voterService.updateVoter(id, voter);
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVoter(@PathVariable Long id) {
        voterService.deleteVoter(id);
    }
}