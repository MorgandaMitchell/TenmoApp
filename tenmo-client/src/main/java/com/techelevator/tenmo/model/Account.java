package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.util.List;

public class Account {

    long id;
    BigDecimal balance;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
