package com.angkorteam.fintech.client.function;

import com.angkorteam.fintech.client.dto.*;

public interface OrganisationApi {

    FineractResponse officeTransactionDelete(String tenant, String token, long transactionId);

    FineractResponse officeTransactionCreate(String tenant, String token, PostOfficeTransactionRequest requestBody);

    FineractResponse officeUpdate(String tenant, String token, long officeId, PutOfficeRequest requestBody);

    FineractResponse officeCreate(String tenant, String token, PostOfficeRequest requestBody);
}
