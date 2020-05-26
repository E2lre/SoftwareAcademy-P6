package com.paymybuddy.paysystem.web.controller;

import com.paymybuddy.paysystem.doa.BankTransfertDao;
import com.paymybuddy.paysystem.model.BankTransfert;
import com.paymybuddy.paysystem.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
public class Transaction2Controller {
    @Autowired
    private BankTransfertDao bankTransfertDao;
    /*---------------------------  Find All -----------------------------*/
    @GetMapping(value = "Banktransactions")
    public List<BankTransfert> ListBankTransaction2Dao() {
        return bankTransfertDao.findAll();
        //return null;
    }
    /*---------------------------  Post -----------------------------*/
    @PostMapping(value="/Banktransaction")
    public BankTransfert saveBankTransaction2Dao(@RequestBody BankTransfert bankTransfert) {
        BankTransfert bt = new BankTransfert(); //TODO a retirer
        bt.setTransfertOrder(99999999);
        bt.setAccount(1);
        bt.setDescription("test");


        return bankTransfertDao.save(bankTransfert);
        /*return bankTransaction2Dao.save(bt);*/
    }
}
