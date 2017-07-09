package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class GlobalConfigurationHelper {

    public static JsonNode updateValueForGlobalConfiguration(final String id, final String value) throws UnirestException {
        final String GLOBAL_CONFIG_UPDATE_URL = "/fineract-provider/api/v1/configurations/" + id;
        JsonNode node = new com.angkorteam.fintech.dto.JsonNode();
        node.getObject().put("value", value);
        return Helper.performServerPut(GLOBAL_CONFIG_UPDATE_URL, node);
    }

    public static JsonNode updateEnabledFlagForGlobalConfiguration(final String id, final boolean enabled) throws UnirestException {
        final String GLOBAL_CONFIG_UPDATE_URL = "/fineract-provider/api/v1/configurations/" + id;
        JsonNode node = new com.angkorteam.fintech.dto.JsonNode();
        node.getObject().put("enabled", enabled);
        return Helper.performServerPut(GLOBAL_CONFIG_UPDATE_URL, node);
    }

}
