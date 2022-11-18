package com.mmacedoaraujo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.mmacedoaraujo.domain.User;
import com.mmacedoaraujo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> returnAllUsersRegistered() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> returnAnUserById(@PathVariable Long id) {
        User userFound = userService.getUserByIdOrThrowException(id);
        return new ResponseEntity<>(userFound, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> registerNewUser(@RequestBody User user) {
        User userSaved = userService.save(user);

        return new ResponseEntity<>(userSaved, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> partialUpdate(@PathVariable Long id, @RequestBody JsonPatch patch) {
        try {
            User userFound = userService.getUserByIdOrThrowException(id);
            User userPatched = userService.applyPatchToCustomer(patch, userFound);
            User updatedUser = userService.save(userPatched);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}