package com.angkorteam.fintech.client.function;

import com.angkorteam.fintech.client.dto.*;

public interface InfrastructureApi {

    PostAuthenticationResponse authentication(String tenant, AuthenticateRequest requestBody);

    FineractResponse accountNumberFormatCreate(String tenant, String token, PostAccountNumberFormatsRequest requestBody);

    FineractResponse accountNumberFormatUpdate(String tenant, String token, long accountNumberFormatId, PutAccountNumberFormatsRequest requestBody);

    FineractResponse accountNumberFormatDelete(String tenant, String token, long accountNumberFormatId);

    FineractResponse cacheSwitch(String tenant, String token, PutCachesRequest requestBody);

    FineractResponse emailCreate(String tenant, String token, PostEmailRequest requestBody);

    FineractResponse emailUpdate(String tenant, String token, long resourceId, PutEmailRequest requestBody);

    FineractResponse emailDelete(String tenant, String token, long resourceId);

    FineractResponse emailConfigurationUpdate(String tenant, String token, PutEmailConfigurationRequest requestBody);

    FineractResponse emailCampaignCreate(String tenant, String token, PostEmailCampaignRequest requestBody);

    FineractResponse emailCampaignUpdate(String tenant, String token, long resourceId, PutEmailCampaignRequest requestBody);

    FineractResponse emailCampaignActivate(String tenant, String token, long resourceId, PostEmailCampaignActivationRequest requestBody);

    FineractResponse emailCampaignReactivate(String tenant, String token, long resourceId, PostEmailCampaignActivationRequest requestBody);

    FineractResponse emailCampaignClose(String tenant, String token, long resourceId, PostEmailCampaignCloseRequest requestBody);

    FineractResponse emailCampaignPreview(String tenant, String token);

    FineractResponse emailCampaignDelete(String tenant, String token, long resourceId);

    FineractResponse codeValueCreate(String tenant, String token, long codeId, PostCodeValuesDataRequest requestBody);

    FineractResponse codeValueUpdate(String tenant, String token, long codeId, long codeValueId, PutCodeValuesDataRequest requestBody);

    FineractResponse codeValueDelete(String tenant, String token, long codeId, long codeValueId);

}
