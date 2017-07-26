package com.angkorteam.fintech.helper;

import org.json.JSONObject;

import com.angkorteam.fintech.IMifos;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class PasswordPreferencesHelper {

    public static JsonNode update(IMifos session, String id) throws UnirestException {
        JsonNode node = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());
        node.getObject().put("validationPolicyId", id);
        return Helper.performServerPut(session, "/api/v1/passwordpreferences", node);
    }

}
