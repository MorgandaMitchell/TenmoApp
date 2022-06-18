package com.techelevator.tenmo.model;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

public class Transfer {

    private final static String[] TRANSFER_STATUS_DESCRIPTION = new String[]{"Pending", "Approved", "Rejected"};
    private int transferStatus;
    private int transferType;
    private Long transferId;
    private Long accountFrom;
    private Long accountTo;
    private String accountFromString = null;
    private String accountToString = null;
    private BigDecimal transferAmount;
    private boolean transferIsRequest;
    private LocalDate dateStamp;
    private List<Transfer> transferHistory;
    private List<Transfer> pendingRequest;


    public Transfer(int transferStatus, int transferType, Long transferId, Long accountFrom, Long accountTo, String accountFromString, String accountToString,
                    BigDecimal transferAmount, boolean transferIsRequest, LocalDate dateStamp, List<Transfer> transferHistory, List<Transfer> pendingRequest) {
        this.transferStatus = transferStatus;
        this.transferType = transferType;
        this.transferId = transferId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.accountFromString = accountFromString;
        this.accountToString = accountToString;
        this.transferAmount = transferAmount;
        this.transferIsRequest = transferIsRequest;
        this.transferHistory = transferHistory;
        this.pendingRequest = pendingRequest;


        dateStamp.now();

    }

    public Transfer() {

    }
    public int getTransferType() {
        return transferType;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
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

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public boolean isTransferIsRequest() {
        return transferIsRequest;
    }

    public void setTransferIsRequest(boolean transferIsRequest) {
        this.transferIsRequest = transferIsRequest;
    }


    public List<Transfer> getTransferHistory() {
        return transferHistory;
    }

    public void setTransferHistory(List<Transfer> transferHistory) {
        this.transferHistory = transferHistory;
    }

    public List<Transfer> getPendingRequest() {
        return pendingRequest;
    }

    public void setPendingRequest(List<Transfer> pendingRequest) {
        this.pendingRequest = pendingRequest;
    }
}
