package com.paymybuddy.paysystem.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;


@Entity
@Table(name="banktransfert")
@DiscriminatorValue("BankTransfert")
public class BankTransfert extends Transaction {

    private static final Logger logger = LogManager.getLogger(BankTransfert.class);

    private int transfertOrder;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "banktransfert_bankinfo_fk"),name = "bankinfo_id")
    @MapsId
    private BankInfo bankinfo;

    public BankTransfert() {

    }
    public BankTransfert( int transfertOrder) {
        this.transfertOrder = transfertOrder;
    }

    public int getTransfertOrder() {
        return transfertOrder;
    }

    public void setTransfertOrder(int transfertOrder) {
        this.transfertOrder = transfertOrder;
    }

}
