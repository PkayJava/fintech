package com.angkorteam.fintech.client.function;

import com.angkorteam.fintech.client.dto.*;
import com.angkorteam.fintech.client.enums.PutUserRequest;

import java.util.Map;

public interface AdministrationApi {

    FineractResponse passwordPreferenceUpdate(String tenant, String token, PutPasswordPreferencesTemplateRequest requestBody);

    FineractResponse userCreate(String tenant, String token, PostUserRequest requestBody);

    FineractResponse userUpdate(String tenant, String token, long userId, PutUserRequest requestBody);

    FineractResponse userDelete(String tenant, String token, long userId);

    FineractResponse makerCheckerPermissionUpdate(String tenant, String token, Map<String, Boolean> requestBody);

    FineractResponse roleCreate(String tenant, String token, PostRoleRequest requestBody);

    FineractResponse roleDisable(String tenant, String token, long roleId);

    FineractResponse roleEnable(String tenant, String token, long roleId);

    FineractResponse roleDelete(String tenant, String token, long roleId);

    FineractResponse rolePermissionUpdate(String tenant, String token, long roleId, Map<String, Boolean> permissions);

}
