package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class AccountController {


    private AccountDao dao;

    public AccountController(AccountDao dao) {
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
