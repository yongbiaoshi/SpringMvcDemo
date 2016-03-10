package com.tsingda.smd.model;

public class Account {
    private String accountId;

    private String userId;

    private Double balance;

    private Short type;

    private Short frozenStatus;

    private Short accountSource;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getFrozenStatus() {
        return frozenStatus;
    }

    public void setFrozenStatus(Short frozenStatus) {
        this.frozenStatus = frozenStatus;
    }

    public Short getAccountSource() {
        return accountSource;
    }

    public void setAccountSource(Short accountSource) {
        this.accountSource = accountSource;
    }
}