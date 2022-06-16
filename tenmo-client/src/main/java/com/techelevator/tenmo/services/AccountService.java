package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser user;

    public AccountService(String baseUrl, AuthenticatedUser user) {
        this.baseUrl = baseUrl;
        this.user = user;
    }

    public AccountService() {

    }

    public BigDecimal viewCurrentBalance() {

        BigDecimal returnDecimal = BigDecimal.valueOf(0);

        try {
            ResponseEntity<BigDecimal> response = restTemplate.exchange(baseUrl + user.getUser().getId() + "/balance", HttpMethod.GET, makeAuthEntity(), BigDecimal.class);
            returnDecimal = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return returnDecimal;
    }

    public BigDecimal sendBucks(long receiverId, long senderId, BigDecimal amount) {

        Transfer transfer = new Transfer(1, null, senderId, receiverId, null, null, amount, false);

        try {
            restTemplate.put(baseUrl, makeAuthEntity(transfer));
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return viewCurrentBalance();
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(user.getToken());
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Transfer> makeAuthEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(user.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(transfer, headers);
    }

}
