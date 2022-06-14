package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface AccountDao {

    Account getAccount(int id);

    List<Transfer> transferHistory(int id);
}
