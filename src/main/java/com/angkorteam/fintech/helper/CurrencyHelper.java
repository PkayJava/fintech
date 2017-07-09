package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class CurrencyHelper {

    private static final String CURRENCY_URL = "/fineract-provider/api/v1/currencies";

    public static JsonNode updateCurrency(final List<String> currencies) throws UnirestException {
        JSONArray array = new JSONArray(currencies);
        JsonNode body = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());
        body.getObject().put("currencies", array);
        return Helper.performServerPut(CURRENCY_URL, body);
    }

}
