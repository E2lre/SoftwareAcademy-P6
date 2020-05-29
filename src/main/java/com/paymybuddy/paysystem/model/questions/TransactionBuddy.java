package com.paymybuddy.paysystem.model.questions;

public class TransactionBuddy {

    private String myEmail;
    private String buddyEmail;
    private String description;
    private double transactionAmount;
    private double feeAmount;

    public TransactionBuddy() {

    }

    public TransactionBuddy(String myEmail, String buddyEmail, String description, double transactionAmount, double feeAmount) {
        this.myEmail = myEmail;
        this.buddyEmail = buddyEmail;
        this.description = description;
        this.transactionAmount = transactionAmount;
        this.feeAmount = feeAmount;
    }

    public String getMyEmail() {
        return myEmail;
    }

    public void setMyEmail(String myEmail) {
        this.myEmail = myEmail;
    }

    public String getBuddyEmail() {
        return buddyEmail;
    }

    public void setBuddyEmail(String buddyEmail) {
        this.buddyEmail = buddyEmail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public double getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(double feeAmount) {
        this.feeAmount = feeAmount;
    }



}
