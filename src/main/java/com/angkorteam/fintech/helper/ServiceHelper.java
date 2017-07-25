package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ServiceHelper {

    public static JsonNode update(JsonNode object) throws UnirestException {
        String type = (String) object.getObject().remove("type");
        return Helper.performServerPut("/fineract-provider/api/v1/externalservice/" + type, object);
    }

}
