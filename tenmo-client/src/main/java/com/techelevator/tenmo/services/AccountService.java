package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    private String baseUrl;
    private RestTemplate restTemplate;
    private AuthenticatedUser user;


    public AccountService(String baseUrl, AuthenticatedUser user) {
        this.baseUrl = baseUrl;
        this.user = user;
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

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(user.getToken());
        return new HttpEntity<>(headers);
    }

}
