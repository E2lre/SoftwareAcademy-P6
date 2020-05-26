package com.paymybuddy.paysystem.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;

@Entity
@Table(name="payment")
@DiscriminatorValue("Payment")
public class Payment extends Transaction {

    private static final Logger logger = LogManager.getLogger(Payment.class);

/*    @Id
    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="transaction_id",foreignKey = @ForeignKey(name = "transaction_buddytransaction_fk"))
    private Transaction transaction;*/

    private double feeAmount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(
            foreignKey = @ForeignKey(name = "buddy_payment_fk2"), value =
            {
                    @JoinColumn( name = "buddy_person_id", referencedColumnName="person_id"),
                    @JoinColumn( name = "buddy_friend_person_id", referencedColumnName="friend_person_id")
            }
    )
    private Buddy buddy;


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



}