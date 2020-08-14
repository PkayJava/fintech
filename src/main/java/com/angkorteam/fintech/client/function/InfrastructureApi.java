package com.angkorteam.fintech.client.function;

import com.angkorteam.fintech.client.dto.AuthenticateRequest;
import com.angkorteam.fintech.client.dto.PostAuthenticationResponse;

public interface InfrastructureApi {

    PostAuthenticationResponse authentication(String tenant, AuthenticateRequest requestBody);

}
