package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private JdbcUserDao dao;

    public UserController(JdbcUserDao dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<User> allUsers() {
        return dao.findAll();
    }

    @GetMapping("/userid/{String}")
    public int findIdByUsername(@PathVariable String username) {
        return dao.findIdByUsername(username);
    }

    @GetMapping("username/{String}")
    public User findByUsername(@PathVariable String username) {
        return dao.findByUsername(username);
    }

    @PostMapping
    public boolean createUser(@Valid @RequestBody String username, String password) {
        return dao.create(username, password);
    }

}
