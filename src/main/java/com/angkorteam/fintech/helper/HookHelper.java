package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/27/17.
 */
public class HookHelper {

    private static final String CREATE_HOOK_URL = "/fineract-provider/api/v1/hooks";

    public static JsonNode create(JsonNode hook) throws UnirestException {
        return Helper.performServerPost(CREATE_HOOK_URL, hook);
    }

    public static JsonNode update(JsonNode hook) throws UnirestException {
        String id = (String) hook.getObject().remove("id");
        final String UPDATE_HOOK_URL = "/fineract-provider/api/v1/hooks/" + id;
        return Helper.performServerPut(UPDATE_HOOK_URL, hook);
    }

    public static JsonNode delete(String id) throws UnirestException {
        final String DELETE_HOOK_URL = "/fineract-provider/api/v1/hooks/" + id;
        return Helper.performServerDelete(DELETE_HOOK_URL);
    }

}
