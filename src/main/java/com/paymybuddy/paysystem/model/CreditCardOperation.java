package com.paymybuddy.paysystem.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="creditcartoperation")
@DiscriminatorValue("CreditCartOperation")
public class CreditCardOperation extends Transaction {
    private static final Logger logger = LogManager.getLogger(CreditCardOperation.class);


    private int creditCardNumber;
    private Date expirationDate;
    private int ccv;
    private int creditCardOrder;

    public CreditCardOperation() {
    }

    public CreditCardOperation(Long id, double amount, String description, Date transactionDate) {
        super(id, amount, description, transactionDate);
    }



    public int getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(int creditCardNumber) {
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
