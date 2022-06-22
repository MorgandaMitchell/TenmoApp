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

    @GetMapping("/pending/{id}")
    public List<Transfer> pendingTransfers(@PathVariable long id){
        return transferDao.getPendingRequests(id);
    }


    @GetMapping("/{id}/balance")
    public BigDecimal getBalanceById(@PathVariable long id) {
        return transferDao.getBalance(id);
    }

    @PutMapping
    public void sendBucks(@RequestBody Transfer transfer) {

        transfer.setSenderAccountId(accountDao.getAccount(transfer.getSenderId()).getId());
        transfer.setRecipientAccountId(accountDao.getAccount(transfer.getRecipientId()).getId());
        transfer.setTransferStatus(2);
        transfer.setTransferType(2);

        try {
            if (transfer.getTransferAmount().compareTo(transferDao.getBalance(transfer.getSenderId())) <= 0) {
                if (transfer.getSenderId() != transfer.getRecipientId()) {
                    transferDao.addTransfer(transfer);
                    transferDao.sendTBucks(transfer);
                    transferDao.receiveTBucks(transfer.getRecipientId(), transfer.getTransferAmount());
                }
            }
        } catch (ResourceAccessException e) {
            System.err.println("We are having some trouble with your send request. Please try again.");
        }
    }

    @PutMapping(path = "/request")
    public void receiveTBucksRequest(@RequestBody Transfer transfer) {

        transfer.setSenderAccountId(accountDao.getAccount(transfer.getSenderId()).getId());
        transfer.setRecipientAccountId(accountDao.getAccount(transfer.getRecipientId()).getId());
        transfer.setTransferStatus(1);
        transfer.setTransferType(1);

        try {
            transferDao.addTransfer(transfer);
        } catch (Exception e) {
            System.out.println();
        }
    }
}
