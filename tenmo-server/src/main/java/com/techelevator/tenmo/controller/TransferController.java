package com.techelevator.tenmo.controller;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transfer")

public class TransferController {
    @Autowired
    private JdbcTransferDao transferDao;

    @GetMapping("/transfer_history/{id}")
    public List<Transfer> historyOfUserTransfers(@PathVariable long id){
        return transferDao.getTransferHistory(id);
    }

    @GetMapping("/pending_transfers")
    public List<Transfer> pendingTransfers(){
        return transferDao.getPendingRequests();
    }





}
