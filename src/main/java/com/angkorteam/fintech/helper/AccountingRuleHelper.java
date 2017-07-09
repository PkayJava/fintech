package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 7/3/17.
 */
public class AccountingRuleHelper {

    public static JsonNode createRule(JsonNode rule) throws UnirestException {
        return Helper.performServerPost("/fineract-provider/api/v1/accountingrules", rule);
    }

    public static JsonNode updateRule(JsonNode rule) throws UnirestException {
        String id = (String) rule.getObject().remove("id");
        return Helper.performServerPut("/fineract-provider/api/v1/accountingrules/" + id, rule);
    }

    public static JsonNode deleteRule(String ruleId) throws UnirestException {
        return Helper.performServerDelete("/fineract-provider/api/v1/accountingrules/" + ruleId);
    }

}
