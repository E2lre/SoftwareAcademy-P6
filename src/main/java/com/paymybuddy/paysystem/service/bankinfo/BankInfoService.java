package com.paymybuddy.paysystem.service.bankinfo;

import com.paymybuddy.paysystem.model.BankInfo;

import java.util.List;

public interface BankInfoService {
    public BankInfo saveBankInfo (BankInfo bankInfo);
    public List<BankInfo> getBankInfo (String email);
}
