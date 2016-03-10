package com.tsingda.smd.service.impl;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.tsingda.service.payment.api.model.TAccount;
import com.tsingda.smd.config.factory.PaymentServiceClientFactory;
import com.tsingda.smd.model.Account;
import com.tsingda.smd.service.AccountService;
import com.tsingda.utils.TConvertUtils;

@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private PaymentServiceClientFactory paymentServiceClientFactory;
    
    @Override
    public Account selectAccountByAccountId(String accountId) throws TException {
        TAccount tAccount = paymentServiceClientFactory.getClient().selectAcountByTAccountId(accountId);
        return TConvertUtils.convertToCommonModel(tAccount, TAccount._Fields.values(), Account.class);
    }

}
