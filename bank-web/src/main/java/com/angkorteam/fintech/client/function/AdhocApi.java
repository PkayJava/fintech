package com.angkorteam.fintech.client.function;

import com.angkorteam.fintech.client.dto.FineractResponse;
import com.angkorteam.fintech.client.dto.PostAdhocRequest;
import com.angkorteam.fintech.client.dto.PutAdhocRequest;

public interface AdhocApi {

    FineractResponse adhocCreate(String tenant, String token, PostAdhocRequest requestBody);

    FineractResponse adhocUpdate(String tenant, String token, long adhocId, PutAdhocRequest requestBody);

    FineractResponse adhocDelete(String tenant, String token, long adhocId);

}
