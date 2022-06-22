package com.techelevator.tenmo.controller;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private JdbcTransferDao transferDao;
    @Autowired
    private JdbcAccountDao accountDao;

    public TransferController(JdbcTransferDao transferDao, JdbcAccountDao accountDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }

    @GetMapping("/history/{id}")
    public List<Transfer> historyOfUserTransfers(@PathVariable long id){
        return transferDao.getTransferHistory(id);
    }

    @GetMapping("/pending_transfers")
    public List<Transfer> pendingTransfers(){
        return transferDao.getPendingRequests();
    }


    @GetMapping("/{id}/balance")
    public BigDecimal getBalanceById(@PathVariable long id) {
        return transferDao.getBalance(id);
    }

    @PutMapping
    public void sendBucks(@RequestBody Transfer transfer) {
        System.out.println(transfer.getSenderId() + " " + transfer.getRecipientId() + " " + transfer.getTransferAmount());
        BigDecimal money = transfer.getTransferAmount();

        long senderAccountId = accountDao.getAccount(transfer.getSenderId()).getId();
        long recipientAccountId = accountDao.getAccount(transfer.getRecipientId()).getId();

        try {
            if (money.compareTo(transferDao.getBalance(transfer.getSenderId())) <= 0) {
                System.out.println("Balance is more than amount to transfer!");
                if (transfer.getSenderId() != transfer.getRecipientId()) {
                    transferDao.addSendTransfer(senderAccountId, recipientAccountId, transfer.getTransferAmount());
                    transferDao.sendTBucks(transfer.getRecipientId(), transfer.getTransferAmount());
                    transferDao.receivingTBucks(transfer.getSenderId(), transfer.getTransferAmount());
                }
            }
            else System.out.println("Balance not large enough!");
        } catch (ResourceAccessException e) {
            System.err.println("try again!");
        }
    }

    @PutMapping(path = "/receive")
    public void receivingTBucks(@RequestBody Transfer transfer) {
        System.out.println(transfer.getRecipientId() + " " + transfer.getSenderId() + " " + transfer.getTransferAmount());
        BigDecimal money = transfer.getTransferAmount();
        try {
            if (money.compareTo(transferDao.getBalance(transfer.getSenderId())) <= 0) {
                System.out.println("Balance is more than amount to transfer!");
                if (transfer.getSenderId() != transfer.getRecipientId()) {
                    transferDao.sendTBucks(transfer.getSenderId(), transfer.getTransferAmount());
                    transferDao.receivingTBucks(transfer.getRecipientId(), transfer.getTransferAmount());
                    transfer.setTransferStatus(1);
                    transfer.setTransferType(1);
                }
            }
            else System.out.println("Balance not large enough!");
        } catch (ResourceAccessException e) {
            System.err.println("try again!");
        }
    }


}
