package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 7/3/17.
 */
public class AccountingRuleHelper {

    public static JsonNode create(JsonNode object) throws UnirestException {
        return Helper.performServerPost("/fineract-provider/api/v1/accountingrules", object);
    }

    public static JsonNode update(JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPut("/fineract-provider/api/v1/accountingrules/" + id, object);
    }

    public static JsonNode delete(String ruleId) throws UnirestException {
        return Helper.performServerDelete("/fineract-provider/api/v1/accountingrules/" + ruleId);
    }

}
