package com.paymybuddy.paysystem.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name="banktransaction")
public class BankTransaction implements Serializable {
    private static final Logger logger = LogManager.getLogger(BankInfo.class);

    @Id
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="transaction_id",foreignKey = @ForeignKey(name = "transaction_banktransaction_fk"))
    private Transaction transaction;
    private int flow;

    public BankTransaction() {

    }
    public BankTransaction( int flow) {
        this.flow = flow;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }


}
