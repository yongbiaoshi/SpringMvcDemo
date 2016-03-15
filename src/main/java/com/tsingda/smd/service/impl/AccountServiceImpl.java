package com.tsingda.smd.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tsingda.service.payment.api.model.TAccount;
import com.tsingda.smd.aspect.annotation.NeedZkLock;
import com.tsingda.smd.aspect.annotation.NeedZkLock.ZkLockType;
import com.tsingda.smd.config.factory.PaymentServiceClientFactory;
import com.tsingda.smd.model.Account;
import com.tsingda.smd.service.AccountService;
import com.tsingda.utils.TConvertUtils;

@Service
public class AccountServiceImpl implements AccountService {
    private final String lockBase = "/payment/com.tsingda.service.payment.api.PaymentService/locks";
    private final String accountMutexLockParent = lockBase + "/account";
    private final String accountMutexLock = accountMutexLockParent + "/lock";

    @Resource
    private PaymentServiceClientFactory paymentServiceClientFactory;

    @Override
    @NeedZkLock(zkLockType = ZkLockType.MUTEX, zkLockNode = accountMutexLock)
    public Account selectAccountByAccountId(String accountId) throws Exception {
        TAccount tAccount = paymentServiceClientFactory.getClient().selectAcountByTAccountId(accountId);
        return TConvertUtils.convertToCommonModel(tAccount, TAccount._Fields.values(), Account.class);
    }

}
