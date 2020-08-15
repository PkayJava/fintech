package com.angkorteam.fintech.client.dto;

import com.angkorteam.fintech.client.renums.FinancialActivity;
import com.google.gson.annotations.SerializedName;

public class PostFinancialActivityAccountsRequest {

    @SerializedName("financialActivityId")
    private FinancialActivity financialActivity;

    private long glAccountId;

    public FinancialActivity getFinancialActivity() {
        return financialActivity;
    }

    public void setFinancialActivity(FinancialActivity financialActivity) {
        this.financialActivity = financialActivity;
    }

    public long getGlAccountId() {
        return glAccountId;
    }

    public void setGlAccountId(long glAccountId) {
        this.glAccountId = glAccountId;
    }

}
