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

    FineractResponse accountingRuleCreate(String tenant, String token, PostAccountingRulesRequest requestBody);

    FineractResponse accountingRuleUpdate(String tenant, String token, long accountingRuleId, PutAccountingRulesRequest requestBody);

    FineractResponse accountingRuleDelete(String tenant, String token, long accountingRuleId);

    FineractResponse financialActivityAccountCreate(String tenant, String token, PostFinancialActivityAccountsRequest requestBody);

    FineractResponse financialActivityAccountUpdate(String tenant, String token, long mappingId, PutFinancialActivityAccountsRequest requestBody);

    FineractResponse financialActivityAccountDelete(String tenant, String token, long mappingId);

    FineractResponse glAccountCreate(String tenant, String token, PostGLAccountsRequest requestBody);

    FineractResponse glAccountUpdate(String tenant, String token, long glAccountId, PutGLAccountsRequest requestBody);

    FineractResponse glAccountDelete(String tenant, String token, long glAccountId);

    FineractResponse journalEntryCreate(String tenant, String token, PostJournalEntriesRequest requestBody);

    FineractResponse journalEntryUpdate(String tenant, String token, PostJournalEntryUpdateRequest requestBody);

    FineractResponse journalEntryDefine(String tenant, String token, PostJournalEntriesRequest requestBody);

    FineractResponse journalEntryReverse(String tenant, String token, long transactionId, PostJournalEntryReverseRequest requestBody);

    FineractResponse provisioningEntryCreate(String tenant, String token, PostProvisioningEntriesRequest requestBody);

    FineractResponse provisioningJournalEntryCreate(String tenant, String token, long entryId);

    FineractResponse provisioningEntryReCreate(String tenant, String token, long entryId);

}
