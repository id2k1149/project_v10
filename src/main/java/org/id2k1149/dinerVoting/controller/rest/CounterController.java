package org.id2k1149.dinerVoting.controller.rest;

import lombok.RequiredArgsConstructor;
import org.id2k1149.dinerVoting.model.VoiceCounter;
import org.id2k1149.dinerVoting.service.CounterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/votes")
@RequiredArgsConstructor
public class CounterController {
    private final CounterService counterService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<VoiceCounter>> getCounters() {
        return ResponseEntity.ok().body(counterService.getCounters());
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<VoiceCounter> getCounter(@PathVariable Long id) {
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/votes/{id}")
                .toUriString());
        return ResponseEntity.created(uri).body(counterService.getCounter(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<VoiceCounter> addCounter(@RequestBody VoiceCounter newVoiceCounter) {
        VoiceCounter created = counterService.addCounter(newVoiceCounter);
        URI uriOfNewResource = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/votes/{id}")
                .toUriString());
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCounter(
            @RequestBody VoiceCounter voiceCounter,
            @PathVariable Long id
    ) {
        counterService.updateCounter(id, voiceCounter);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCounter(@PathVariable Long id) {
        counterService.deleteCounter(id);
    }

    @GetMapping(path = "today")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public List<VoiceCounter> getTodayResults() {
        return counterService.getAllResults();
    }

    @GetMapping(path = "best")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public VoiceCounter getBestResult() {
        return counterService.getBestResult();
    }
}
