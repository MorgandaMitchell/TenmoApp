package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    @Autowired
    private UserDao dao;

    public AccountController(UserDao dao) {
        this.dao = dao;
    }

//    @GetMapping("/{id}")
//    public Account getAccountById(@PathVariable int id) {
//        return dao.getAccountById;
//    }

    @GetMapping("/{id}/balance")
    public BigDecimal getBalanceById(@PathVariable Long id) {
        return dao.getBalance(id);
    }

}
