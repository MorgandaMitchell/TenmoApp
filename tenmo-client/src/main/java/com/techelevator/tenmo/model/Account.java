package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.util.List;

public class Account {

    long id;
    BigDecimal balance;
//    List<Transfer> transferHistory;

    public Account(long id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public Account() {
    }

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

//    public List<Transfer> getTransferHistory() {
//        return transferHistory;
//    }
//
//    public void setTransferHistory(List<Transfer> transferHistory) {
//        this.transferHistory = transferHistory;
//    }
}
