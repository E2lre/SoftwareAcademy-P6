package com.paymybuddy.paysystem.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="banktransaction2")
@DiscriminatorValue("Bank")
public class BankTransaction2 extends Transaction2 {
    private static final Logger logger = LogManager.getLogger(BankInfo.class);

/*    @Id
    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="transaction_id",foreignKey = @ForeignKey(name = "transaction_banktransaction_fk2"))
    private Transaction transaction;*/
    private int flow;

    public BankTransaction2() {

    }
    public BankTransaction2( int flow) {
        this.flow = flow;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

}
