package com.vvornicova.lab.controller;

import com.vvornicova.lab.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {

    private List<User> userList = new ArrayList<>();

    @GetMapping
    public List<User> getAllUsers() {
        return userList;
    }

    @PostMapping
    public void postUser(@RequestBody User user) {
        userList.add(user);
    }

    @DeleteMapping
    public void deleteUser(@RequestBody Long id) {
        userList.stream().filter(u -> Objects.equals(u.getId(), id)).findFirst().ifPresent(u -> userList.remove(u));
    }

    @PutMapping
    public void updateUser(@RequestBody User user) {
        userList.stream()
                .filter(u -> Objects.equals(u.getId(), user.getId()))
                .findFirst()
                .ifPresent(u -> {
                    u.setFirstName(user.getFirstName());
                    u.setLastName(user.getLastName());
                    u.setEmail(user.getEmail());
                });
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userList.stream()
                .filter(u -> Objects.equals(u.getId(), id))
                .findFirst()
                .orElse(null);
    }
}
