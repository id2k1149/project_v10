package org.id2k1149.dinerVoting.controller.rest;

import lombok.RequiredArgsConstructor;
import org.id2k1149.dinerVoting.model.Diner;
import org.id2k1149.dinerVoting.model.Menu;
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
@RequestMapping(path = "api/v1/diners")
@RequiredArgsConstructor
public class DinerController {
    private final DinerService dinerService;
    private final MenuService menuService;
    private final CounterService counterService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<Diner>> getAnswers() {
        return ResponseEntity.ok().body(dinerService.getDiners());
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Diner> getAnswer(@PathVariable Long id) {
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/diners/{id}")
                .toUriString());
        return ResponseEntity.created(uri).body(dinerService.getDiner(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Diner> addAnswer(@RequestBody Diner newDiner) {
        Diner created = dinerService.addDiner(newDiner);
        URI uriOfNewResource = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/diners/{id}")
                .toUriString());
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAnswer(
            @RequestBody Diner diner,
            @PathVariable Long id
    ) {
        dinerService.updateDiner(diner, id);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnswer(@PathVariable Long id) {
        dinerService.deleteDiner(id);
    }

    @GetMapping("/today")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public List<DinerTo> getTodayInfo() {
        return DinerUtil.getDinersTo(
                menuService.getDinersMenuByDate(LocalDate.now()),
                menuService.getByDate(LocalDate.now()));
    }

    @GetMapping(path = "{id}/menu")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public DinerTo getAllMenuForDiner(@PathVariable Long id) {
        return dinerService.getAllMenuForDiner(id);
    }

    @PostMapping(path = "{id}/menu", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void addMenuForDiner(
            @RequestBody Menu menu,
            @PathVariable Long id) {
        menuService.addMenu(menu, id);
    }

    @GetMapping(path = "{id}/today")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public DinerTo getTodayMenuForDiner(@PathVariable Long id) {
        return dinerService.getTodayMenuForDiner(id);
    }


    @PostMapping(path = "{id}/vote", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public void voteForDiner(
            @PathVariable Long id) {
        counterService.voteForDiner(id);
    }
}