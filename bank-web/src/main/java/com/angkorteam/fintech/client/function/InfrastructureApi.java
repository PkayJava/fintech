package com.angkorteam.fintech.client.function;

import com.angkorteam.fintech.client.dto.*;
import com.angkorteam.fintech.client.enums.TableTypeEnum;

import java.util.Map;

public interface InfrastructureApi {

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

    FineractResponse smsCampaignCreate(String tenant, String token);

    FineractResponse smsCampaignUpdate(String tenant, String token, long campaignId);

    FineractResponse smsCampaignDelete(String tenant, String token, long campaignId);

    FineractResponse smsCampaignPreview(String tenant, String token, long campaignId);

    FineractResponse smsCampaignReactivate(String tenant, String token, long campaignId);

    FineractResponse smsCampaignActivate(String tenant, String token, long campaignId);

    FineractResponse smsCampaignClose(String tenant, String token, long campaignId);

    FineractResponse codeCreate(String tenant, String token, CodeRequest requestBody);

    FineractResponse codeUpdate(String tenant, String token, long codeId, CodeRequest requestBody);

    FineractResponse codeDelete(String tenant, String token, long codeId);

    FineractResponse codeValueCreate(String tenant, String token, long codeId, PostCodeValueRequest requestBody);

    FineractResponse codeValueUpdate(String tenant, String token, long codeId, long codeValueId, PutCodeValueRequest requestBody);

    FineractResponse codeValueDelete(String tenant, String token, long codeId, long codeValueId);

    FineractResponse externalServiceUpdate(String tenant, String token);

    FineractResponse configurationUpdate(String tenant, String token);

    FineractResponse creditBureauUpdate(String tenant, String token);

    FineractResponse creditBureauMappingUpdate(String tenant, String token);

    FineractResponse creditBureauOrganisationCreate(String tenant, String token);

    FineractResponse creditBureauProductMappingCreate(String tenant, String token);

    FineractResponse datatableCreate(String tenant, String token, PostDataTableRequest requestBody);

    FineractResponse datatableUpdate(String tenant, String token, String datatableName, PutDataTableRequest requestBody);

    FineractResponse datatableDelete(String tenant, String token, String datatableName);

    FineractResponse datatableRegister(String tenant, String token, String datatable, TableTypeEnum apptable, PostDataTableRegisterRequest requestBody);

    FineractResponse datatableDeregister(String tenant, String token, String datatable);

    FineractResponse datatableEntryCreate(String tenant, String token, String datatable, long apptableId, Map<String, Object> requestBody);

    FineractResponse datatableEntryOne2OneUpdate(String tenant, String token, String datatable, long apptableId, Map<String, Object> requestBody);

    FineractResponse datatableEntryOne2ManyUpdate(String tenant, String token, String datatable, long apptableId, long datatableId, Map<String, Object> requestBody);

    FineractResponse datatableEntryClear(String tenant, String token, String datatable, long apptableId);

    FineractResponse datatableEntryDelete(String tenant, String token, String datatable, long apptableId, long datatableId);

    FineractResponse datatableEntityCheckCreate(String tenant, String token, PostDatatableEntityCheckRequest requestBody);

    FineractResponse datatableEntityCheckDelete(String tenant, String token, long entityDatatableCheckId);

    FineractResponse reportCreate(String tenant, String token);

    FineractResponse reportUpdate(String tenant, String token);

    FineractResponse reportDelete(String tenant, String token);

    FineractResponse documentCreate(String tenant, String token);

    FineractResponse documentUpdate(String tenant, String token);

    FineractResponse documentDelete(String tenant, String token);

    FineractResponse imageCreate(String tenant, String token);

    FineractResponse imageCreate(String tenant, String token, long abc);

    FineractResponse imageUpdate(String tenant, String token);

    FineractResponse imageUpdate(String tenant, String token, long abc);

    FineractResponse imageDelete(String tenant, String token);

    FineractResponse entityToEntityCreate(String tenant, String token, long relId, EntityToEntityRequest requestBody);

    FineractResponse entityToEntityUpdate(String tenant, String token, long mapId, EntityToEntityRequest requestBody);

    FineractResponse entityToEntityDelete(String tenant, String token, long mapId);

    FineractResponse deviceRegistration(String tenant, String token);

    FineractResponse deviceUpdate(String tenant, String token);

    FineractResponse deviceDelete(String tenant, String token);

    FineractResponse hookCreate(String tenant, String token, PostHookRequest requestBody);

    FineractResponse hookUpdate(String tenant, String token, long hookId, PutHookRequest requestBody);

    FineractResponse hookDelete(String tenant, String token, long hookId);

    FineractResponse jobStart(String tenant, String token);

    FineractResponse jobStop(String tenant, String token);

    FineractResponse jobExecute(String tenant, String token);

    FineractResponse jobDetailUpdate(String tenant, String token);

    FineractResponse reportMailingCreate(String tenant, String token);

    FineractResponse reportMailingUpdate(String tenant, String token);

    FineractResponse reportMailingDelete(String tenant, String token);

    PostAuthenticationResponse authentication(String tenant, AuthenticateRequest requestBody);

    PostAuthenticationResponse tokenRequest(String tenant, AuthenticateRequest requestBody);

    PostAuthenticationResponse tokenValidation(String tenant, AuthenticateRequest requestBody);

    PostAuthenticationResponse tokenUpdate(String tenant, AuthenticateRequest requestBody);

    PostAuthenticationResponse twoFactorConfigurationUpdate(String tenant, AuthenticateRequest requestBody);

    FineractResponse smsCreate(String tenant, String token);

    FineractResponse smsUpdate(String tenant, String token);

    FineractResponse smsDelete(String tenant, String token);

    FineractResponse likelihoodUpdate(String tenant, String token);

}