package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import io.github.openunirest.http.JsonNode;

public class ServiceHelper {

    public static JsonNode update(IMifos session, JsonNode object) {
        String type = (String) object.getObject().remove("type");
        return Helper.performServerPut(session, "/api/v1/externalservice/" + type, object);
    }

}
