package org.id2k1149.project_v10.controllerRest;

import lombok.RequiredArgsConstructor;
import org.id2k1149.project_v10.model.Info;
import org.id2k1149.project_v10.service.InfoService;
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
@RequestMapping(path = "api/v1/info")
@RequiredArgsConstructor
public class InfoController {
    private final InfoService infoService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<Info>> getAllInfo() {
        return ResponseEntity.ok().body(infoService.getAllInfo());
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Info> getInfo(@PathVariable Long id) {
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/info/{id}")
                .toUriString());
        return ResponseEntity.created(uri).body(infoService.getInfo(id));
    }

    @PostMapping(value = "{answerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Info> addInfo(@RequestBody Info newInfo, @PathVariable Long answerId) {
        Info created = infoService.addInfo(newInfo, answerId);
        URI uriOfNewResource = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/info/{id}")
                .toUriString());
        return ResponseEntity.created(uriOfNewResource).body(created);

    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInfo(
            @RequestBody Info Info,
            @PathVariable Long id
    ) {
        infoService.updateInfo(Info, id);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInfo(@PathVariable Long id) {
        infoService.deleteInfo(id);
    }

    @GetMapping(path = "/today")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public List<Info> getTodayInfo() {
        return infoService.getByDate(LocalDate.now());
    }
}