package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/tenmo_user")
public class UserController {

    private JdbcUserDao dao;

    public UserController(JdbcUserDao dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<User> allUsers() {
        return dao.findAll();
    }

    @GetMapping("/search/{username}")
    public int findIdByUsername(@PathVariable String username) {
        return dao.findIdByUsername(username);
    }

    @GetMapping("username/{username}")
    public User findByUsername(@PathVariable String username) {
        return dao.findByUsername(username);
    }

    @PostMapping
    public boolean createUser(@Valid @RequestBody String username, String password) {
        return dao.create(username, password);
    }

    @PutMapping(path = "")
    public void sendBucks(@RequestBody Transfer transfer) {
        System.out.println(transfer.getAccountFrom() + " " + transfer.getAccountTo() + " " + transfer.getAmount());
        BigDecimal money = transfer.getAmount();
        try {
            if (money.compareTo(dao.getBalance(transfer.getAccountFrom())) <= 0) {
                System.out.println("Balance is more than amount to transfer!");
                if (!transfer.getAccountFrom().equals(transfer.getAccountTo())) {
                    dao.sendTBucks(transfer.getAccountFrom(), transfer.getAmount());
                    dao.receivingTBucks(transfer.getAccountTo(), transfer.getAmount());
                    transfer.setTransferStatus(1);
                }
            }
            else System.out.println("Balance not large enough!");
        } catch (ResourceAccessException e) {
            System.err.println("try again!");
        }
    }


}
