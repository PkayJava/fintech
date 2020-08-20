package com.angkorteam.fintech.client.function;

import com.angkorteam.fintech.client.dto.FineractResponse;

public interface NotificationApi {

    FineractResponse notificationUpdate(String tenant, String token, long auditId);

}
