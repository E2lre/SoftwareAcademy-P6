package com.paymybuddy.paysystem.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="creditcartoperation")
@DiscriminatorValue("CreditCartOperation")
public class CreditCardOperation extends Transaction {
    private static final Logger logger = LogManager.getLogger(CreditCardOperation.class);

    private int creditCardOrder;

    public CreditCardOperation(int creditCardOrder) {
        this.creditCardOrder = creditCardOrder;
    }

    public CreditCardOperation() {

    }

    public int getCreditCardOrder() {
        return creditCardOrder;
    }

    public void setCreditCardOrder(int creditCardOrder) {
        this.creditCardOrder = creditCardOrder;
    }


}
