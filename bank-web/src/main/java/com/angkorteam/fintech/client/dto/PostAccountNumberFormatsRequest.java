package com.angkorteam.fintech.client.dto;

import com.angkorteam.fintech.client.renums.AccountNumberPrefixType;
import com.angkorteam.fintech.client.renums.EntityAccountType;

public class PostAccountNumberFormatsRequest {

    private EntityAccountType accountType;

    private AccountNumberPrefixType prefixType;

    public EntityAccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(EntityAccountType accountType) {
        this.accountType = accountType;
    }

    public AccountNumberPrefixType getPrefixType() {
        return prefixType;
    }

    public void setPrefixType(AccountNumberPrefixType prefixType) {
        this.prefixType = prefixType;
    }

}
