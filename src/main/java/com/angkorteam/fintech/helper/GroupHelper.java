package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class GroupHelper {

    public static JsonNode create(IMifos session, JsonNode object) throws UnirestException {
        return Helper.performServerPost(session, "/api/v1/groups", object);
    }

    public static JsonNode update(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPut(session, "/api/v1/groups/" + id, object);
    }

    public static JsonNode delete(IMifos session, String id) throws UnirestException {
        return Helper.performServerDelete(session, "/api/v1/groups/" + id);
    }

}
