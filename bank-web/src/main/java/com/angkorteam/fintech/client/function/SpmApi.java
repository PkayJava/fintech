package com.angkorteam.fintech.client.function;

import com.angkorteam.fintech.client.dto.FineractResponse;
import com.angkorteam.fintech.client.dto.PostLookupTableRequest;
import com.angkorteam.fintech.client.dto.PostScoreRequest;
import com.angkorteam.fintech.client.dto.SurveyRequest;

public interface SpmApi {

    FineractResponse surveyCreate(String tenant, String token, SurveyRequest requestBody);

    FineractResponse surveyUpdate(String tenant, String token, long surveyId, SurveyRequest requestBody);

    FineractResponse surveyDeactivate(String tenant, String token, long surveyId);

    FineractResponse surveyActivate(String tenant, String token, long surveyId);

    FineractResponse surveyScoreCreate(String tenant, String token, long surveyId, PostScoreRequest requestBody);

    FineractResponse surveyLookupTableCreate(String tenant, String token, long surveyId, PostLookupTableRequest requestBody);

}
