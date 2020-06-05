package com.paymybuddy.paysystem.web.controller;


import com.paymybuddy.paysystem.model.BankInfo;
import com.paymybuddy.paysystem.service.bankinfo.BankInfoService;
import com.paymybuddy.paysystem.web.exceptions.BankInfoCanNotBeFoundException;
import com.paymybuddy.paysystem.web.exceptions.BankInfoCanNotbeAddedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BankInfoController {

    private static final Logger logger = LogManager.getLogger(BankInfoController.class);

    @Autowired
    private BankInfoService bankInfoService;

/*---------------------------  Find Bankinfo by email -----------------------------*/

    /**
     * List bank information for a person
     * @param email Email of the person
     * @return List of information bank
     * @throws BankInfoCanNotBeFoundException error if the person doesn't exist, ...
     */
    @GetMapping(value = "bankinfo/{email}")
    public List<BankInfo> listBankInfo(@PathVariable String email) throws BankInfoCanNotBeFoundException {
        logger.info("listBankInfo start ");
        List<BankInfo> bankInfoListResult =null;
        bankInfoListResult = bankInfoService.getBankInfo(email);
        if (bankInfoListResult == null) {
            throw new BankInfoCanNotBeFoundException(" BankInfo can not be found for email "+ email);
        }
        logger.info("listBankInfo finsih ");
        return bankInfoListResult;
    }

/*--------------------------- POST : Creation d'un bankinfo pour un email ----------------*/

    /**
     * Create info bank for a person
     * @param bankInfo Info bank for the person (contain email)
     * @return Info bank created
     * @throws BankInfoCanNotbeAddedException Error if the person doesn't exist, ...
     */
    @PostMapping(value="/bankinfo")
    @ResponseStatus(HttpStatus.CREATED)
    public BankInfo saveBankinfo(@RequestBody BankInfo bankInfo) throws BankInfoCanNotbeAddedException {
        logger.info("SaveBankinfo start : " + bankInfo);

        BankInfo bankinfoResult = bankInfoService.saveBankInfo(bankInfo);


        if (bankinfoResult == null) {
            throw new BankInfoCanNotbeAddedException(" BankInfo not created for email "+ bankInfo.getPerson().getEmail());
        }
        logger.info("SaveBankinfo finsih ");
        return bankinfoResult;
    }

}
