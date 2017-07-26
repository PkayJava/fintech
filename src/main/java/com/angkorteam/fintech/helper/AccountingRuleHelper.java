package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 7/3/17.
 */
public class AccountingRuleHelper {

    public static JsonNode create(IMifos session, JsonNode object) throws UnirestException {
        return Helper.performServerPost(session, "/api/v1/accountingrules", object);
    }

    public static JsonNode update(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPut(session, "/api/v1/accountingrules/" + id, object);
    }

    public static JsonNode delete(IMifos session, String id) throws UnirestException {
        return Helper.performServerDelete(session, "/api/v1/accountingrules/" + id);
    }

}
