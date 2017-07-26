package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/27/17.
 */
public class HookHelper {

    public static JsonNode create(IMifos session, JsonNode hook) throws UnirestException {
        return Helper.performServerPost(session, "/api/v1/hooks", hook);
    }

    public static JsonNode update(IMifos session, JsonNode hook) throws UnirestException {
        String id = (String) hook.getObject().remove("id");
        return Helper.performServerPut(session, "/api/v1/hooks/" + id, hook);
    }

    public static JsonNode delete(IMifos session, String id) throws UnirestException {
        return Helper.performServerDelete(session, "/api/v1/hooks/" + id);
    }

}
