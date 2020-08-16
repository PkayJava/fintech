package com.angkorteam.fintech.client.function;

import com.angkorteam.fintech.client.dto.FineractResponse;

public interface MakerCheckerApi {

    FineractResponse makerCheckerApprove(String tenant, String token, long auditId);

    FineractResponse makerCheckerReject(String tenant, String token, long auditId);

    FineractResponse makerCheckerDelete(String tenant, String token, long auditId);

}
