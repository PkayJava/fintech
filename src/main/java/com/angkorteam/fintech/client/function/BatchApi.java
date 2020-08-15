package com.angkorteam.fintech.client.function;

import com.angkorteam.fintech.client.dto.FineractResponse;
import com.angkorteam.fintech.client.dto.PostBatchesRequest;

public interface BatchApi {

    FineractResponse batchRequest(String tenant, String token, boolean enclosingTransaction, PostBatchesRequest requestBody);

}
