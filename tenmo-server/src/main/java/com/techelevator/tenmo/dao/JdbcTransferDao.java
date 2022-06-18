package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcTransferDao implements TransferDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Transfer> getTransferHistory(long accountId) {
        List<Transfer> transferHistory = new ArrayList<>();
        String sql = "SELECT account_from FROM transfer WHERE account_from = ? OR account_to = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql + accountId, accountId);
        while(results.next()) {
            Transfer transfer = mapRowTransfer(results);
            transferHistory.add(transfer);
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
    public Transfer getTransferWithId(long transferId) {
        return null;
    }

    @Override
    public String sendTransfer(long senderId, long receiverId, BigDecimal amount) {
        return null;
    }

    @Override
    public String requestTransfer(long senderId, long receiverId, BigDecimal amount) {
        return null;
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
        transfer.setAccountFrom(rs.getLong("account_from"));
        transfer.setAccountTo(rs.getLong("account_to"));
        transfer.setTransferAmount(rs.getBigDecimal("amount"));

        return transfer;
    }


}
