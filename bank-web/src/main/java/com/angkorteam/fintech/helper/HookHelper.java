package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/27/17.
 */
public class HookHelper {

    public static JsonNode create(IMifos session, JsonNode object) {
        return Helper.performServerPost(session, "/api/v1/hooks", object);
    }

    public static JsonNode update(IMifos session, JsonNode object) {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPut(session, "/api/v1/hooks/" + id, object);
    }

    public static JsonNode delete(IMifos session, String id) {
        return Helper.performServerDelete(session, "/api/v1/hooks/" + id);
    }

}
