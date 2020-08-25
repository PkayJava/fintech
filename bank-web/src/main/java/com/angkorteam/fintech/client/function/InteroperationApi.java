package com.angkorteam.fintech.client.function;

import com.angkorteam.fintech.client.dto.FineractResponse;
import com.angkorteam.fintech.client.dto.PutStaffRequest;

public interface InteroperationApi {

    FineractResponse interoperationTransfer(String tenant, String token, long staffId, PutStaffRequest requestBody);

    FineractResponse interoperationQuoteCreate(String tenant, String token, long staffId, PutStaffRequest requestBody);

    FineractResponse interoperationTransactionCreate(String tenant, String token, long staffId, PutStaffRequest requestBody);

    FineractResponse accountIdentifierDelete(String tenant, String token, long staffId, PutStaffRequest requestBody);

    FineractResponse accountIdentifierDelete(String tenant, String token, long staffId, PutStaffRequest requestBody, long abc);

    FineractResponse accountIdentifierRegister(String tenant, String token, long staffId, PutStaffRequest requestBody);

    FineractResponse accountIdentifierRegister(String tenant, String token, long staffId, PutStaffRequest requestBody, long abc);
}
