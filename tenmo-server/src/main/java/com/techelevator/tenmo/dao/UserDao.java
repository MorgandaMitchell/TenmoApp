package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserDao {

    List<User> findAll();

    User findByUsername(String username);

    int findIdByUsername(String username);

    BigDecimal getBalance(long id);

    void receivingTBucks(long receiverId, BigDecimal amount);

    void sendTBucks(long senderId, BigDecimal amount);

    boolean create(String username, String password);

}
