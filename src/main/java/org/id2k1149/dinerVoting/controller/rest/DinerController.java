package org.id2k1149.dinerVoting.controller.rest;

import lombok.RequiredArgsConstructor;
import org.id2k1149.dinerVoting.model.Diner;
import org.id2k1149.dinerVoting.service.DinerService;
import org.id2k1149.dinerVoting.service.CounterService;
import org.id2k1149.dinerVoting.service.MenuService;
import org.id2k1149.dinerVoting.to.DinerTo;
import org.id2k1149.dinerVoting.util.DinerUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = DinerController.REST_URL)
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
@RequiredArgsConstructor
public class DinerController {
    private final DinerService dinerService;
    private final MenuService menuService;
    private final CounterService counterService;
    static final String REST_URL = "/api/v1/diners";

    @GetMapping
    public List<Diner> getDiners() {
        return dinerService.getDiners();
    }

    @GetMapping(path = "{id}")
    public Diner getDiner(@PathVariable Long id) {
        return dinerService.getDiner(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Diner> addDiner(@RequestBody Diner newDiner) {
        Diner created = dinerService.addDiner(newDiner);
        URI uriOfNewResource = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .toUriString());
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDiner(
            @RequestBody Diner diner,
            @PathVariable Long id
    ) {
        dinerService.updateDiner(diner, id);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDiner(@PathVariable Long id) {
        dinerService.deleteDiner(id);
    }

    @GetMapping("/today")
    public List<DinerTo> getTodayMenuForAllDiners() {
        return DinerUtil.getDinersTo(
                menuService.getDinersMenuByDate(LocalDate.now()),
                menuService.getByDate(LocalDate.now()));
    }

    @GetMapping(path = "{id}/today")
    public DinerTo getTodayMenuForDiner(@PathVariable Long id) {
        return dinerService.getTodayMenuForDiner(id);
    }


    @PostMapping(path = "{id}/vote", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void voteForDiner(
            @PathVariable Long id) {
        counterService.voteForDiner(id);
    }
}