package com.angkorteam.fintech.helper;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.angkorteam.fintech.IMifos;
import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/26/17.
 * CurrenciesApiResource
 */
public class CurrencyHelper {

    public static JsonNode update(IMifos session, List<String> currencies) {
        JSONArray array = new JSONArray(currencies);
        JsonNode body = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());
        body.getObject().put("currencies", array);
        return Helper.performServerPut(session, "/api/v1/currencies", body);
    }

}
