package com.paymybuddy.paysystem.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Table(name="banktransfert")
@DiscriminatorValue("BankTransfert")
public class BankTransfert extends Transaction {

    @NotNull
    private int transfertOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "banktransfert_bankinfo_fk"),name = "bankinfo_id")
    private BankInfo bankinfo;

    public BankTransfert() {

    }
    public BankTransfert(Long id, double amount, String description, Date transactionDate) {
        super(id, amount, description, transactionDate);
    }

    public int getTransfertOrder() {
        return transfertOrder;
    }

    public void setTransfertOrder(int transfertOrder) {
        this.transfertOrder = transfertOrder;
    }

    public BankInfo getBankinfo() {
        return bankinfo;
    }

    public void setBankinfo(BankInfo bankinfo) {
        this.bankinfo = bankinfo;
    }

}
