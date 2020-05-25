package com.paymybuddy.paysystem.web.controller;

import com.paymybuddy.paysystem.doa.BankTransaction2Dao;
import com.paymybuddy.paysystem.model.BankTransaction2;
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
    private BankTransaction2Dao bankTransaction2Dao;
    /*---------------------------  Find All -----------------------------*/
    @GetMapping(value = "Banktransactions")
    public List<BankTransaction2> ListBankTransaction2Dao() {
        return bankTransaction2Dao.findAll();
        //return null;
    }
    /*---------------------------  Post -----------------------------*/
    @PostMapping(value="/Banktransaction")
    public BankTransaction2 saveBankTransaction2Dao(@RequestBody BankTransaction2 bankTransaction2) {
        BankTransaction2 bt = new BankTransaction2();
        bt.setFlow(0);
        bt.setAccount(1);
        bt.setDescription("toto");


        return bankTransaction2Dao.save(bankTransaction2);
        /*return bankTransaction2Dao.save(bt);*/
    }
}
