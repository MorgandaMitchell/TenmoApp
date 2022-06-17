package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class AccountService {

    private String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;

    public AccountService(String baseUrl, AuthenticatedUser currentUser) {
        this.baseUrl = baseUrl;
        this.currentUser = currentUser;
    }

    public AccountService() {

    }

    public BigDecimal viewCurrentBalance() {

        BigDecimal returnDecimal = BigDecimal.valueOf(0);

        try {
            ResponseEntity<BigDecimal> response = restTemplate.exchange(baseUrl + currentUser.getUser().getId() + "/balance", HttpMethod.GET, makeAuthEntity(), BigDecimal.class);
            returnDecimal = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return returnDecimal;
    }

    public BigDecimal sendBucks() {

        Transfer transfer = new Transfer();
        BigDecimal amount;

        try {
            Scanner scan = new Scanner(System.in);

            transfer.setAccountTo(Long.parseLong(scan.nextLine()));
            transfer.setAccountFrom(currentUser.getUser().getId());

            if (transfer.getAccountTo() != 0) {
                System.out.println("Please enter an amount: ");
            }

            try {
                amount = scan.nextBigDecimal();
                transfer.setTransferAmount(amount);
            } catch (NumberFormatException e) {
                System.out.println("Something went wrong while entering amount to transfer.");
            }

            restTemplate.put(baseUrl + "/tenmo_user", makeAuthEntity(transfer));
        } catch (DataAccessException e) {
            System.out.println();
        }

        return viewCurrentBalance();
    }

//    public BigDecimal sendBucks(long receiverId, long senderId, BigDecimal amount) {
//
//        Transfer transfer = new Transfer(1, null, senderId, receiverId, null, null, amount, false);
//
//        try {
//            restTemplate.put(baseUrl, makeAuthEntity(transfer));
//        } catch (DataAccessException e) {
//            System.out.println();
//        }
//
//        return viewCurrentBalance();
//    }

    public User[] getAllUsers() {

        User[] users = null;

        try {
            ResponseEntity<User[]> response = restTemplate.exchange(baseUrl + "/tenmo_user", HttpMethod.GET, makeAuthEntity(), User[].class);
            users = response.getBody();
        } catch (Exception e) {
            System.out.println("Get all users error.");
        }
        return users;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Transfer> makeAuthEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(transfer, headers);
    }

}
