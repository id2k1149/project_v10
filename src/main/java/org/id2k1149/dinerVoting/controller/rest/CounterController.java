package org.id2k1149.dinerVoting.controller.rest;

import lombok.RequiredArgsConstructor;
import org.id2k1149.dinerVoting.model.Counter;
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
@RequestMapping(path = CounterController.REST_URL)
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
@RequiredArgsConstructor
public class CounterController {
    private final CounterService counterService;
    static final String REST_URL = "/api/v1/counters";

    @GetMapping
    public List<Counter> getCounters() {
        return counterService.getCounters();
    }

    @GetMapping(path = "{id}")
    public Counter getCounter(@PathVariable Long id) {
        return counterService.getCounter(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Counter> addCounter(@RequestBody Counter newCounter) {
        Counter created = counterService.addCounter(newCounter);
        URI uriOfNewResource = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .toUriString());
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCounter(
            @RequestBody Counter counter,
            @PathVariable Long id
    ) {
        counterService.updateCounter(id, counter);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCounter(@PathVariable Long id) {
        counterService.deleteCounter(id);
    }

    @GetMapping(path = "today")
    public List<Counter> getTodayResults() {
        return counterService.getAllResults();
    }

    @GetMapping(path = "best")
    public Counter getBestResult() {
        return counterService.getBestResult();
    }
}
