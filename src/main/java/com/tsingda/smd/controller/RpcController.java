package com.tsingda.smd.controller;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tsingda.service.payment.api.PaymentServiceConstants;
import com.tsingda.smd.config.factory.PaymentServiceClientFactory;
import com.tsingda.smd.model.Account;
import com.tsingda.smd.service.AccountService;

@Controller
public class RpcController {

    private static String SERVICE_PATH = String.format("/%s/%s/%s/%s", "rpc", PaymentServiceConstants.PRODUCT,
            PaymentServiceConstants.SERVICE_NAME, PaymentServiceConstants.SERVICE_VERSION);
    
    @Autowired
    private CuratorFramework zkCleint;

    @Autowired
    private PaymentServiceClientFactory clientFactory;

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "rpc/account/{accountId}")
    public @ResponseBody Account getAccount(@PathVariable String accountId) throws Exception {
        return accountService.selectAccountByAccountId(accountId);
    }
}
