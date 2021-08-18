package org.id2k1149.project_v10.restController;

import lombok.RequiredArgsConstructor;
import org.id2k1149.project_v10.model.User;
import org.id2k1149.project_v10.service.UserService;
import org.id2k1149.project_v10.to.UserVotesTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/users/{id}")
                .toUriString());
        return ResponseEntity.created(uri).body(userService.getUser(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User created = userService.addUser(user);
        URI uriOfNewResource = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/users/{id}")
                .toUriString());
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(
            @RequestBody User user,
            @PathVariable Long id
    ) {
        userService.updateUser(user, id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping(path = "/{id}/votes")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public UserVotesTo getUserVotes(@PathVariable Long id) {
        return userService.getUserAllVotes(id);
    }
}