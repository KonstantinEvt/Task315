package ru.kata.spring.boot_security.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.DtoUser;
import ru.kata.spring.boot_security.demo.exeption_handing.UserNotFoundException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class RestCont {
    private final UserService userService;
    private final RoleService roleService;

    public RestCont(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getListUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = (userService.getUser(id));
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody DtoUser dtoUser) {
        userService.addUser(dtoUser);
        return new ResponseEntity<>(userService.findUserByUsername(dtoUser.getUsername()), HttpStatus.OK);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody DtoUser dtoUser, @PathVariable("id") Long id) {
        userService.updateUser(dtoUser, id);
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> removeUser(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.getListRoles();
    }


    @GetMapping("/users/auth")
    public ResponseEntity<User> getAuthInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String str = authentication.getName();
        System.out.println(str);
        User user = userService.findUserByUsername(authentication.getName());

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
