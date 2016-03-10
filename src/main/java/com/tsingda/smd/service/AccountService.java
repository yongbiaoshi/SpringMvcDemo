package com.tsingda.smd.service;

import org.apache.thrift.TException;

import com.tsingda.smd.model.Account;

public interface AccountService {
    Account selectAccountByAccountId(String accountId) throws TException;
}
