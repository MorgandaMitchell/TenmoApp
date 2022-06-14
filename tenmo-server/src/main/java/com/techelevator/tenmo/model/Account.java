package com.techelevator.tenmo.model;

import java.util.List;

public class Account {

    long id;
    double balance;
    List<Transfer> transferHistory;

    public Account(long id, double balance) {
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transfer> getTransferHistory() {
        return transferHistory;
    }

    public void setTransferHistory(List<Transfer> transferHistory) {
        this.transferHistory = transferHistory;
    }
}
