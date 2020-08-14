package com.angkorteam.fintech.client.function;

import com.angkorteam.fintech.client.dto.FineractResponse;
import com.angkorteam.fintech.client.dto.PostRolesRequest;
import com.angkorteam.fintech.client.dto.PutPasswordPreferencesTemplateRequest;
import com.angkorteam.fintech.client.dto.PutPermissionsRequest;

public interface AdministrationApi {

    FineractResponse passwordPreferenceUpdate(String tenant, String token, PutPasswordPreferencesTemplateRequest requestBody);

    FineractResponse permissionUpdate(String tenant, String token, PutPermissionsRequest requestBody);

    FineractResponse roleCreate(String tenant, String token, PostRolesRequest requestBody);

    FineractResponse roleCommand(String tenant, String token, long roleId, String command);

}
