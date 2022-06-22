package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcTransferDao implements TransferDao {
    private final JdbcTemplate jdbcTemplate;
    private JdbcAccountDao accountDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Transfer> getTransferHistory(long userId) {

        ArrayList<Transfer> transferHistory = new ArrayList<>();
        String sql = "SELECT t.transfer_id, t.transfer_type_id, t.transfer_status_id, t.account_from, t.account_to, t.amount, x.username AS sender,  y.username AS recipient\n" +
                "FROM transfer t\n" +
                "JOIN account acfrom ON acfrom.account_id = t.account_from\n" +
                "JOIN account acto ON acto.account_id = t.account_to\n" +
                "JOIN tenmo_user x ON acfrom.user_id = x.user_id\n" +
                "JOIN tenmo_user y ON acto.user_id = y.user_id\n" +
                "WHERE acto.user_id = ? OR acfrom.user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
        while (results.next()) {
            transferHistory.add(mapRowTransfer(results));
        }
        return transferHistory ;
    }

    @Override
    public List<Transfer> getPendingRequests(long userId) {
        List<Transfer> pending = new ArrayList<>();
        String sql = "SELECT t.transfer_id, t.transfer_type_id, t.transfer_status_id, t.account_from, t.account_to, t.amount, x.username AS sender,  y.username AS recipient\n" +
                "FROM transfer t\n" +
                "JOIN account acfrom ON acfrom.account_id = t.account_from\n" +
                "JOIN account acto ON acto.account_id = t.account_to\n" +
                "JOIN tenmo_user x ON acfrom.user_id = x.user_id\n" +
                "JOIN tenmo_user y ON acto.user_id = y.user_id\n" +
                "WHERE t.transfer_type_id = 1 AND (acto.user_id = ? OR acfrom.user_id = ?);";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
        while(results.next()) {
            Transfer transfer = mapRowTransfer(results);
            pending.add(transfer);
        }
        return pending;
    }

    @Override
    public BigDecimal getBalance(long id) {

        BigDecimal balance = null;
        String sql = "Select balance FROM account WHERE account.user_id = ?;";

        try {
            balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return balance;
    }

    @Override
    public void receiveTBucks(long receiverId, BigDecimal amount) {

        String sql = "UPDATE account SET balance = balance + ? WHERE account.user_id = ?";

        try{
            jdbcTemplate.update(sql, amount, receiverId);
        } catch (DataAccessException e) {
            System.err.println("Error receiving money.");

        }
    }

    @Override
    public void sendTBucks(Transfer transfer) {

        String sql = "UPDATE account SET balance = balance - ? WHERE account.user_id = ?;";

        try{
            jdbcTemplate.update(sql, transfer.getTransferAmount(), transfer.getSenderId());
        } catch (DataAccessException e) {
            System.err.println("Error receiving money.");
        }
    }

    @Override
    public Transfer getTransferWithId(long transferId) {
        return null;
    }

    @Override
    public void addTransfer(Transfer transfer) {

        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?,?,?,?,?);";

        try {
            if (transfer.getTransferType() == 2) {
                jdbcTemplate.queryForObject(sql, Long.class, transfer.getTransferType(), transfer.getTransferStatus(), transfer.getSenderAccountId(), transfer.getRecipientAccountId(), transfer.getTransferAmount());
            }  else{
                jdbcTemplate.queryForObject(sql, Long.class, transfer.getTransferType(), transfer.getTransferStatus(), transfer.getRecipientAccountId(), transfer.getSenderAccountId(), transfer.getTransferAmount());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String updateTransferRequest(Transfer transfer, long statusId) {
        return null;
    }

    private Transfer mapRowTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getLong("transfer_id"));
        transfer.setTransferType(rs.getInt("transfer_type_id"));
        transfer.setTransferStatus(rs.getInt("transfer_status_id"));
        transfer.setSenderId(rs.getLong("account_from"));
        transfer.setRecipientId(rs.getLong("account_to"));
        transfer.setTransferAmount(rs.getBigDecimal("amount"));

        try {
            transfer.setSenderUsername(rs.getString("sender"));
            transfer.setRecipientUsername(rs.getString("recipient"));
        } catch (Exception e) {
        }

        return transfer;
    }

}
