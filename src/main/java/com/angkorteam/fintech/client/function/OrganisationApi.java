package com.angkorteam.fintech.client.function;

import com.angkorteam.fintech.client.dto.*;

public interface OrganisationApi {

    FineractResponse officeTransactionDelete(String tenant, String token, long transactionId);

    FineractResponse officeTransactionCreate(String tenant, String token, PostOfficeTransactionRequest requestBody);

    FineractResponse officeUpdate(String tenant, String token, long officeId, PutOfficeRequest requestBody);

    FineractResponse officeCreate(String tenant, String token, PostOfficeRequest requestBody);

    FineractResponse workingDayUpdate(String tenant, String token, PutWorkingDaysRequest requestBody);

    FineractResponse currencyUpdate(String tenant, String token, PutCurrenciesRequest requestBody);

    FineractResponse tellerCreate(String tenant, String token, PostTellersRequest requestBody);

    FineractResponse holidayCreate(String tenant, String token, PostHolidaysRequest requestBody);

    FineractResponse staffCreate(String tenant, String token, PostStaffRequest requestBody);

    FineractResponse staffUpdate(String tenant, String token, long staffId, PutStaffRequest requestBody);

}
