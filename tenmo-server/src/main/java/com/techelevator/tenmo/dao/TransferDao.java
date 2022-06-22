package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {


    List<Transfer> getTransferHistory(long accountId);

    Transfer getTransferWithId(long transferId);

    BigDecimal getBalance(long id);

    void receiveTBucks(long receiverId, BigDecimal amount);

    void sendTBucks(Transfer transfer);

    void addTransfer(Transfer transfer);

    String updateTransferRequest (Transfer transfer, long statusId);

    List<Transfer> getPendingRequests();
}
