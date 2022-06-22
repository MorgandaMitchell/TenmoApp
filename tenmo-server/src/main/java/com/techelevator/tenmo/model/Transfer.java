package com.techelevator.tenmo.model;
import java.math.BigDecimal;
import java.util.List;

public class Transfer {

    private int transferStatus;
    private int transferType;
    private long transferId;
    private long senderId;
    private long recipientId;
    private long senderAccountId;
    private long recipientAccountId;
    private String senderUsername;
    private String recipientUsername;
    private BigDecimal transferAmount;
    private boolean transferIsRequest;


    public Transfer(int transferStatus, int transferType, long transferId, long senderId, long recipientId,
                    BigDecimal transferAmount, boolean transferIsRequest) {
        this.transferStatus = transferStatus;
        this.transferType = transferType;
        this.transferId = transferId;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.transferAmount = transferAmount;
        this.transferIsRequest = transferIsRequest;
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

    public long getTransferId() {
        return transferId;
    }

    public void setTransferId(long transferId) {
        this.transferId = transferId;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(long recipientId) {
        this.recipientId = recipientId;
    }

    public long getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(long senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public long getRecipientAccountId() {
        return recipientAccountId;
    }

    public void setRecipientAccountId(long recipientAccountId) {
        this.recipientAccountId = recipientAccountId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getRecipientUsername() {
        return recipientUsername;
    }

    public void setRecipientUsername(String recipientUsername) {
        this.recipientUsername = recipientUsername;
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
}
