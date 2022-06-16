package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private final static String[] TRANSFER_STATUS_DESCRIPTION = new String[]{"Pending", "Approved", "Rejected"};
    private int transferStatus;
    private Long transferId;
    private Long accountFrom;
    private Long accountTo;
    private String accountFromString = null;
    private String accountToString = null;
    private BigDecimal amount;
    private boolean transferIsRequest;

    public Transfer(int transferStatus, Long transferId, Long accountFrom, Long accountTo, String accountFromString, String accountToString, BigDecimal amount, boolean transferIsRequest) {
        this.transferStatus = transferStatus;
        this.transferId = transferId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.accountFromString = accountFromString;
        this.accountToString = accountToString;
        this.amount = amount;
        this.transferIsRequest = transferIsRequest;
    }

    public Transfer() {

    }

    public int getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(int transferStatus) {
        this.transferStatus = transferStatus;
    }

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public Long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Long accountTo) {
        this.accountTo = accountTo;
    }

    public String getAccountFromString() {
        return accountFromString;
    }

    public void setAccountFromString(String accountFromString) {
        this.accountFromString = accountFromString;
    }

    public String getAccountToString() {
        return accountToString;
    }

    public void setAccountToString(String accountToString) {
        this.accountToString = accountToString;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isTransferIsRequest() {
        return transferIsRequest;
    }

    public void setTransferIsRequest(boolean transferIsRequest) {
        this.transferIsRequest = transferIsRequest;
    }
}
