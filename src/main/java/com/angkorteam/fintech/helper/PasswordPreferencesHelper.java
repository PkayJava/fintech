package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class PasswordPreferencesHelper {

    private static final String PASSWORD_PREFERENCES_URL = "/fineract-provider/api/v1/passwordpreferences";

    public static JsonNode update(String validationPolicyId) throws UnirestException {
        JsonNode node = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());
        node.getObject().put("validationPolicyId", validationPolicyId);
        return Helper.performServerPut(PASSWORD_PREFERENCES_URL, node);
    }

}
