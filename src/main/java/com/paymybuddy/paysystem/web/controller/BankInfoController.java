package com.paymybuddy.paysystem.web.controller;


import com.paymybuddy.paysystem.doa.PersonDao;
import com.paymybuddy.paysystem.model.BankInfo;
import com.paymybuddy.paysystem.model.Person;
import com.paymybuddy.paysystem.service.bankinfo.BankInfoService;
import com.paymybuddy.paysystem.service.person.PersonService;
import com.paymybuddy.paysystem.web.exceptions.BankInfoCanNotbeAddedException;
import com.paymybuddy.paysystem.web.exceptions.PersonCanNotbeAddedException;
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

/*---------------------------  Find Bankinfo by person -----------------------------*/

    @GetMapping(value = "Bankinfo/{email}")
    public List<BankInfo> ListBankInfo(@PathVariable String email) {
        return bankInfoService.getBankInfo(email);
    }
/*--------------------------- POST : Creation d'un bankinfo pour un email ----------------*/

    @PostMapping(value="/Bankinfo")
    @ResponseStatus(HttpStatus.CREATED)
    public BankInfo SaveBankinfo(@RequestBody BankInfo bankInfo) throws BankInfoCanNotbeAddedException {

        logger.info("SaveBankinfo start : " + bankInfo);

        BankInfo bankinfoResult = bankInfoService.saveBankInfo(bankInfo);


        if (bankinfoResult == null) {
            throw new BankInfoCanNotbeAddedException(" BankInfo not created for email "+ bankInfo.getPerson().getEmail());
        }

        return bankinfoResult;
    }

}
