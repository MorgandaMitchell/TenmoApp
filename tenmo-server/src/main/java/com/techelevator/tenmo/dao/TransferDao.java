package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {


    public List<Transfer> getTransferHistory(long accountId);
    public Transfer getTransferWithId(long transferId);
    public String sendTransfer(long senderId, long receiverId, BigDecimal amount);
    public String requestTransfer (long senderId, long receiverId, BigDecimal amount);
    public String updateTransferRequest (Transfer transfer, long statusId);
    public List<Transfer> getPendingRequests();
}
