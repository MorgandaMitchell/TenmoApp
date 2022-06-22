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
    public List<Transfer> getTransferHistory(long accountId) {

        ArrayList<Transfer> transferHistory = new ArrayList<>();
        String sql = "SELECT transfer.*, x.username AS sender,  y.username AS recipient " +
                "FROM transfer " +
                "JOIN account acfrom ON acfrom.account_id = transfer.account_from " +
                "JOIN account acto ON acto.account_id = transfer.account_to " +
                "JOIN tenmo_user x ON acfrom.user_id = x.user_id " +
                "JOIN tenmo_user y ON acto.user_id = y.user_id " +
                "WHERE acto.user_id = ? OR acfrom.user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId, accountId);
        while (results.next()) {
            transferHistory.add(mapRowTransfer(results));
        }
        return transferHistory ;
    }

    @Override
    public List<Transfer> getPendingRequests() {
        List<Transfer> pending = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_status FROM transfer WHERE user_id = ? AND status_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
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
    public void receivingTBucks(long receiverId, BigDecimal amount) {

        String sql = "UPDATE account SET balance = balance - ? WHERE account.user_id = ?";

        try{
            jdbcTemplate.update(sql, amount, receiverId);
        } catch (DataAccessException e) {
            System.err.println("Error receiving money.");

        }
    }

    @Override
    public void sendTBucks(long senderId, BigDecimal amount) {

        String sql = "UPDATE account SET balance = balance + ? WHERE account.user_id = ?;";

        try{
            jdbcTemplate.update(sql, amount, senderId);
        } catch (DataAccessException e) {
            System.err.println("Error receiving money.");
        }
    }

    @Override
    public Transfer getTransferWithId(long transferId) {
        return null;
    }

    @Override
    public Long addSendTransfer(long senderAccountId, long recipientAccountId, BigDecimal amount) {

        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (2,2,?,?,?) RETURNING transfer_id;";

        Long x = null;
        try {
            x = jdbcTemplate.queryForObject(sql, Long.class, senderAccountId, recipientAccountId, amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return x;
    }

    @Override
    public void addRequestTransfer(long senderId, long receiverId, BigDecimal amount) {

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

//    Transfer mapRowToTransfer(SqlRowSet rs)
//    {
//        Transfer transfer = new Transfer();
//        transfer.setTransferId(rs.getLong("transfer_id"));
//        transfer.setTransferStatus(rs.getInt("transfer_status_id"));
//        transfer.setSenderId(rs.getLong("account_from"));
//        transfer.setSenderUsername(JdbcUserDao.findByAccountId(transfer.getAccountFrom()).getUsername());
//        transfer.setRecipientId(rs.getLong("account_to"));
//        transfer.setRecipientUsername(JdbcUserDao.findByAccountId(transfer.getAccountTo()).getUsername());
//        transfer.setTransferAmount(rs.getBigDecimal("amount"));
//        transfer.setTransferIsRequest(rs.getInt("transfer_type_id") == 1);
//        transfer.setRecipientAccountId(accountDao.getAccount(transfer.getRecipientId()).getId());
//        transfer.setSenderAccountId(accountDao.getAccount(transfer.getSenderId()).getId());
//
//        return transfer;
//    }

}
