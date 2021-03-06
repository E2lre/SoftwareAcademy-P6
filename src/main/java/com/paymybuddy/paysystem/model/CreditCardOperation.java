package com.paymybuddy.paysystem.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="creditcardoperation")
@DiscriminatorValue("CreditCardOperation")
public class CreditCardOperation extends Transaction {
    private static final Logger logger = LogManager.getLogger(CreditCardOperation.class);

    @NotNull
    private double creditCardNumber;
    @NotNull
    private Date expirationDate;
    @NotNull
    private int ccv;
    @NotNull
    private int creditCardOrder;

    public CreditCardOperation() {
    }

    public CreditCardOperation(Long id, double amount, String description, Date transactionDate) {
        super(id, amount, description, transactionDate);
    }



    public double getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(double creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Date getExpirationDate() {

        Date localDate = expirationDate;
        return localDate;

    }

    public void setExpirationDate(Date expirationDate) {
        if (expirationDate == null) {
            this.expirationDate = null;
        }
        else {
            this.expirationDate = new Date(expirationDate.getTime());
        }
    }

    public int getCcv() {
        return ccv;
    }

    public void setCcv(int ccv) {
        this.ccv = ccv;
    }


    public int getCreditCardOrder() {
        return creditCardOrder;
    }

    public void setCreditCardOrder(int creditCardOrder) {
        this.creditCardOrder = creditCardOrder;
    }


}
