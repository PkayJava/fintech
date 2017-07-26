package com.angkorteam.fintech.helper;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.angkorteam.fintech.IMifos;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class CurrencyHelper {

    public static JsonNode update(IMifos session, List<String> currencies) throws UnirestException {
        JSONArray array = new JSONArray(currencies);
        JsonNode body = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());
        body.getObject().put("currencies", array);
        return Helper.performServerPut(session, "/api/v1/currencies", body);
    }

}
