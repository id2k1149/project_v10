package org.id2k1149.dinerVoting.controller.rest;

import lombok.RequiredArgsConstructor;
import org.id2k1149.dinerVoting.model.Voter;
import org.id2k1149.dinerVoting.service.VoterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = VoterController.REST_URL)
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
@RequiredArgsConstructor
public class VoterController {
    private final VoterService voterService;
    static final String REST_URL = "/api/v1/voters";

    @GetMapping
    public List<Voter> getVoters() {
        return voterService.getVoters();
    }

    @GetMapping(path = "{id}")
    public Voter getVoter(@PathVariable Long id) {
        return voterService.getVoter(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Voter> addVoter(@RequestBody Voter newVoter) {
        Voter created = voterService.addVoter(newVoter);
        URI uriOfNewResource = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .toUriString());
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVoter(
            @RequestBody Voter voter,
            @PathVariable Long id) {
        voterService.updateVoter(id, voter);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVoter(@PathVariable Long id) {
        voterService.deleteVoter(id);
    }
}