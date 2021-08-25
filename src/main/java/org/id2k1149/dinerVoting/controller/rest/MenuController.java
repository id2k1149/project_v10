package org.id2k1149.dinerVoting.controller.rest;

import lombok.RequiredArgsConstructor;
import org.id2k1149.dinerVoting.model.Menu;
import org.id2k1149.dinerVoting.service.MenuService;
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
@RequestMapping(path = "api/v1/menus")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<Menu>> getAllMenu() {
        return ResponseEntity.ok().body(menuService.getAllMenu());
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Menu> getMenu(@PathVariable Long id) {
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/menus/{id}")
                .toUriString());
        return ResponseEntity.created(uri).body(menuService.getMenu(id));
    }

    @PostMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Menu> addMenu(@RequestBody Menu newMenu, @PathVariable Long id) {
        Menu created = menuService.addMenu(newMenu, id);
        URI uriOfNewResource = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/menus/{id}")
                .toUriString());
        return ResponseEntity.created(uriOfNewResource).body(created);

    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMenu(
            @RequestBody Menu Menu,
            @PathVariable Long id
    ) {
        menuService.updateMenu(Menu, id);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
    }

    @GetMapping(path = "/today")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public List<Menu> getTodayMenu() {
        return menuService.getByDate(LocalDate.now());
    }
}