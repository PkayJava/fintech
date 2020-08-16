package com.angkorteam.fintech.client.dto;

import com.angkorteam.fintech.client.renums.AccountNumberPrefixType;

public class PutAccountNumberFormatsRequest {

    private AccountNumberPrefixType prefixType;

    public AccountNumberPrefixType getPrefixType() {
        return prefixType;
    }

    public void setPrefixType(AccountNumberPrefixType prefixType) {
        this.prefixType = prefixType;
    }

}
