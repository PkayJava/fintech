package com.angkorteam.fintech.client.function;

import com.angkorteam.fintech.client.dto.*;

public interface InfrastructureApi {

    PostAuthenticationResponse authentication(String tenant, AuthenticateRequest requestBody);

    FineractResponse codeValueCreate(String tenant, String token, long codeId, PostCodeValuesDataRequest requestBody);

    FineractResponse codeValueUpdate(String tenant, String token, long codeId, long codeValueId, PutCodeValuesDataRequest requestBody);

    FineractResponse codeValueDelete(String tenant, String token, long codeId, long codeValueId);

}
