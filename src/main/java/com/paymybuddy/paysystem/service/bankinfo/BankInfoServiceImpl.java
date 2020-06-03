package com.paymybuddy.paysystem.service.bankinfo;

import com.paymybuddy.paysystem.dao.BankInfoDao;
import com.paymybuddy.paysystem.dao.PersonDao;
import com.paymybuddy.paysystem.model.BankInfo;
import com.paymybuddy.paysystem.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankInfoServiceImpl implements BankInfoService {
    private static final Logger logger = LogManager.getLogger(BankInfoServiceImpl.class);
    @Autowired
    private BankInfoDao bankInfoDao;
    @Autowired
    private PersonDao personDao;

    public BankInfo saveBankInfo (BankInfo bankInfo){
        BankInfo bankinfoResult = null;
        Person personResult = personDao.findByEmail(bankInfo.getPerson().getEmail());
        if (personResult !=null){
            BankInfo bankinfoToSave = new BankInfo(personResult,bankInfo.getType(),bankInfo.getInfo(),bankInfo.getDescription());
            bankinfoResult = bankInfoDao.save(bankinfoToSave);
        }
        return bankinfoResult;

    }

    public List<BankInfo> getBankInfo (String email){
        List<BankInfo> bankInfoList = null;
        Person personResult = personDao.findByEmail(email);

        if (personResult != null){
            bankInfoList = bankInfoDao.findByPerson(personResult);
        }
        return bankInfoList;

    }

}
