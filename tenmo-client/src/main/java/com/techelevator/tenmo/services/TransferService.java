package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Scanner;

public class TransferService {

    private String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;

    public TransferService(String baseUrl, AuthenticatedUser currentUser) {
        this.baseUrl = baseUrl;
        this.currentUser = currentUser;
    }


    public void sendBucks() {

        Transfer transfer = new Transfer();
        BigDecimal amount;

        try {
            Scanner scan = new Scanner(System.in);

            transfer.setRecipientId(Long.parseLong(scan.nextLine()));
            transfer.setSenderId(currentUser.getUser().getId());

            if (transfer.getRecipientId() != 0) {
                System.out.println("Please enter an amount: ");
            }

            try {
                amount = scan.nextBigDecimal();
                transfer.setTransferAmount(amount);
            } catch (NumberFormatException e) {
                System.out.println("Something went wrong while entering amount to transfer.");
            }

            restTemplate.put(baseUrl + "/transfer", makeTransferEntity(transfer));
        } catch (DataAccessException e) {
            System.out.println();
        }
    }

    public void requestBucks() {

        Transfer transfer = new Transfer();
        BigDecimal amount;

        try {
            Scanner scan = new Scanner(System.in);

            transfer.setRecipientId(Long.parseLong(scan.nextLine()));
            transfer.setSenderId(currentUser.getUser().getId());

            if (transfer.getSenderId() != 0) {
                System.out.println("Please enter an amount: ");
            }

            try {
                amount = scan.nextBigDecimal();
                transfer.setTransferAmount(amount);
            } catch (NumberFormatException e) {
                System.out.println("Something went wrong while entering amount to transfer.");
            }

            restTemplate.put(baseUrl + "/transfer/receive", makeTransferEntity(transfer));
        } catch (DataAccessException e) {
            System.out.println();
        }
    }

    public Transfer[] getTransferHistory(){

        Transfer[] transfers = null;

        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "/transfer/history/" + currentUser.getUser().getId(), HttpMethod.GET, makeTransferEntity(), Transfer[].class);
            transfers = response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transfers;
    }

    private HttpEntity<Void> makeTransferEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(transfer, headers);
    }
}
