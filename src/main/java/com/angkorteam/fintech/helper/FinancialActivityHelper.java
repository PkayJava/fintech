package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public class FinancialActivityHelper {

    public static JsonNode createFinancialActivity(JsonNode financialActivity) throws UnirestException {
        return Helper.performServerPost("/fineract-provider/api/v1/financialactivityaccounts", financialActivity);
    }

    public static JsonNode updateFinancialActivity(JsonNode financialActivity) throws UnirestException {
        String id = (String) financialActivity.getObject().remove("id");
        return Helper.performServerPut("/fineract-provider/api/v1/financialactivityaccounts/" + id, financialActivity);
    }

    public static JsonNode deleteFinancialActivity(String id) throws UnirestException {
        return Helper.performServerDelete("/fineract-provider/api/v1/financialactivityaccounts/" + id);
    }

}
