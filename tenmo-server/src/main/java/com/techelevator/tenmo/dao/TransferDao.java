package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {


    List<Transfer> getTransferHistory(long accountId);

    Transfer getTransferWithId(long transferId);

    BigDecimal getBalance(long id);

    void receivingTBucks(long receiverId, BigDecimal amount);

    void sendTBucks(long senderId, BigDecimal amount);

    Long addSendTransfer(long senderId, long receiverId, BigDecimal amount);

    void addRequestTransfer(long senderId, long receiverId, BigDecimal amount);

    String updateTransferRequest (Transfer transfer, long statusId);

    List<Transfer> getPendingRequests();
}
