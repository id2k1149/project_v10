package org.id2k1149.dinerVoting.controller.rest;

import lombok.RequiredArgsConstructor;
import org.id2k1149.dinerVoting.model.User;
import org.id2k1149.dinerVoting.service.UserService;
import org.id2k1149.dinerVoting.to.UserVotesTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = UserController.REST_URL)
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    static final String REST_URL = "/api/v1/users";

    @GetMapping()
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(path = "/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("permitAll()")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        User created = userService.addUser(user);
        URI uriOfNewResource = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .toUriString());
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(
            @Valid
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserVotesTo getUserVotes(@PathVariable Long id) {
        return userService.getUserAllVotes(id);
    }
}