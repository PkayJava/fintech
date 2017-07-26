package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ChargeHelper {

    public static JsonNode create(IMifos session, JsonNode object) throws UnirestException {
        return Helper.performServerPost(session, "/api/v1/charges", object);
    }

    public static JsonNode update(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPut(session, "/api/v1/charges/" + id, object);
    }

}
