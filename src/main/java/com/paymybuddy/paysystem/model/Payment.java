package com.paymybuddy.paysystem.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="payment")
@DiscriminatorValue("Payment")
public class Payment extends Transaction {

    private static final Logger logger = LogManager.getLogger(Payment.class);

    @NotNull
    @Column(columnDefinition = "Decimal(6,2)")
    private double feeAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "payment_person_fk"),name = "payment_person_id")
    private Person buddyPayment;

    public Payment() {

    }
    public Payment(double feeAmount) {
        this.feeAmount = feeAmount;
    }


    public double getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(double feeAmount) {
        this.feeAmount = feeAmount;
    }

    public Person getBuddyPayment() {
        return buddyPayment;
    }

    public void setBuddyPayment(Person buddyPayment) {
        this.buddyPayment = buddyPayment;
    }

}
