package org.id2k1149.dinerVoting.controller.rest;

import lombok.RequiredArgsConstructor;
import org.id2k1149.dinerVoting.model.Menu;
import org.id2k1149.dinerVoting.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = MenuController.REST_URL)
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;
    static final String REST_URL = "/api/v1/menus";

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public Menu getMenu(@PathVariable Long id) {
        return menuService.getMenu(id);
    }

    @PostMapping(path = "{dinerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addMenuForDiner(
            @RequestBody Menu menu,
            @PathVariable Long dinerId) {
        menuService.addMenu(menu, dinerId);
    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMenu(
            @RequestBody Menu Menu,
            @PathVariable Long id) {
        menuService.updateMenu(Menu, id);
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
    }
}
