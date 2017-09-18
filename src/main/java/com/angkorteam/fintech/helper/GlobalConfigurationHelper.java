package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class GlobalConfigurationHelper {

    public static JsonNode updateValueForGlobalConfiguration(IMifos session, String id, String value) throws UnirestException {
        JsonNode node = new com.angkorteam.fintech.dto.JsonNode();
        node.getObject().put("value", value);
        return Helper.performServerPut(session, "/api/v1/configurations/" + id, node);
    }

    public static JsonNode updateEnabledFlagForGlobalConfiguration(IMifos session, String id, boolean enabled) throws UnirestException {
        JsonNode node = new com.angkorteam.fintech.dto.JsonNode();
        node.getObject().put("enabled", enabled);
        return Helper.performServerPut(session, "/api/v1/configurations/" + id, node);
    }

}
