package com.angkorteam.fintech.client.function;

import com.angkorteam.fintech.client.dto.*;

public interface AccountingApi {

    FineractResponse runAccrualCreate(String tenant, String token, PostRunAccrualRequest requestBody);

    FineractResponse glClosureCreate(String tenant, String token, PostGlClosureRequest requestBody);

    FineractResponse glClosureUpdate(String tenant, String token, long glClosureId, PutGlClosureRequest requestBody);

    FineractResponse glClosureDelete(String tenant, String token, long glClosureId);
}
