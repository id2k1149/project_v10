package org.id2k1149.project_v10.restController;

import lombok.RequiredArgsConstructor;
import org.id2k1149.project_v10.model.Counter;
import org.id2k1149.project_v10.service.CounterService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/votes")
@RequiredArgsConstructor
public class CounterController {
    private final CounterService counterService;

    @GetMapping
    public List<Counter> getCounters() {
        return counterService.getCounters();
    }

    @GetMapping(path = "{id}")
    public Counter getCounter(@PathVariable("id") Long id) {
        return counterService.getCounter(id);
    }

    @PostMapping(path = "{id}")
    public void addCounter(@RequestBody Counter newCounter) {
        counterService.addCounter(newCounter);
    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateCounter(
            @RequestBody Counter counter,
            @PathVariable("id") Long id
    ) {
        counterService.updateCounter(id, counter);
    }

    @DeleteMapping(path = "{id}")
    public void deleteCounter(@PathVariable("id") Long id) {
        counterService.deleteCounter(id);
    }
}