package com.angkorteam.fintech.client.function;

import com.angkorteam.fintech.client.dto.*;

public interface AccountingApi {

    /**
     * Executes Periodic Accrual Accounting
     *
     * @param tenant
     * @param token
     * @param requestBody
     * @return
     */
    FineractResponse runAccrualCreate(String tenant, String token, PostRunAccrualRequest requestBody);

    FineractResponse glClosureCreate(String tenant, String token, PostGLClosureRequest requestBody);

    FineractResponse glClosureUpdate(String tenant, String token, long glClosureId, PutGLClosureRequest requestBody);

    FineractResponse glClosureDelete(String tenant, String token, long glClosureId);

    FineractResponse glAccountCreate(String tenant, String token, PostGLAccountsRequest requestBody);

    FineractResponse accountingRuleCreate(String tenant, String token, PostAccountingRulesRequest requestBody);

}
