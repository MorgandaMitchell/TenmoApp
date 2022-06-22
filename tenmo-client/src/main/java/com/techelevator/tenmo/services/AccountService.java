package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;

public class AccountService {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private final AuthenticatedUser currentUser;

    public AccountService(String baseUrl, AuthenticatedUser currentUser) {
        this.baseUrl = baseUrl;
        this.currentUser = currentUser;
    }

    public BigDecimal viewCurrentBalance() {

        BigDecimal returnDecimal = BigDecimal.valueOf(0);

        try {
            ResponseEntity<BigDecimal> response = restTemplate.exchange(baseUrl + "/transfer/" + currentUser.getUser().getId() + "/balance", HttpMethod.GET, makeAuthEntity(), BigDecimal.class);
            returnDecimal = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return returnDecimal;
    }

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
}
