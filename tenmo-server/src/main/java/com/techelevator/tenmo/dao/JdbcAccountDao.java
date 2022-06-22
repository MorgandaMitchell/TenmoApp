package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getAccount(long userId) {
        String sql = "Select account_id, balance FROM account WHERE user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        if (rowSet.next()) {
            return mapRowToAccount(rowSet);
        }
        return null;
    }

    @Override
    public BigDecimal getBalance(long id) {

        BigDecimal balance = null;
        String sql = "Select balance FROM account WHERE account.user_id = ?;";

        try {
            balance = jdbcTemplate.queryForObject(sql + id, BigDecimal.class);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return balance;
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setId(rs.getLong("account_id"));
        account.setBalance(rs.getDouble("balance"));
        return account;
    }
}
