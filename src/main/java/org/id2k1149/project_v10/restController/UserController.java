package org.id2k1149.project_v10.restController;

import lombok.RequiredArgsConstructor;
import org.id2k1149.project_v10.model.User;
import org.id2k1149.project_v10.service.UserService;
import org.id2k1149.project_v10.to.UserTo;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").toUriString());
        return ResponseEntity.created(uri).body(userService.getUser(id));
    }

    @PostMapping()
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(
            @RequestBody User user,
            @PathVariable("id") Long id
    ) {
        userService.updateUser(user, id);
    }

    @DeleteMapping(path = "{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    @GetMapping(path = "{id}/votes")
    public UserTo getUserVotes(@PathVariable("id") Long id) {
        return userService.getUserAllVotes(id);
    }



}