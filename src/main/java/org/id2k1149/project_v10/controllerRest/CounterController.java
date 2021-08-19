package org.id2k1149.project_v10.controllerRest;

import lombok.RequiredArgsConstructor;
import org.id2k1149.project_v10.model.Counter;
import org.id2k1149.project_v10.service.CounterService;
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
    public ResponseEntity<List<Counter>> getCounters() {
        return ResponseEntity.ok().body(counterService.getCounters());
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Counter> getCounter(@PathVariable Long id) {
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/votes/{id}")
                .toUriString());
        return ResponseEntity.created(uri).body(counterService.getCounter(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Counter> addCounter(@RequestBody Counter newCounter) {
        Counter created = counterService.addCounter(newCounter);
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
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public List<Counter> getTodayResults() {
        return counterService.getAllResults();
    }

    @GetMapping(path = "best")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public Counter getBestResult() {
        return counterService.getBestResult();
    }
}
