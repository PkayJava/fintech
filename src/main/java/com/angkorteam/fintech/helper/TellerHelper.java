package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 7/14/17.
 */
public class TellerHelper {

    public static JsonNode createTeller(JsonNode teller) throws UnirestException {
        return Helper.performServerPost("/fineract-provider/api/v1/tellers", teller);
    }

    public static JsonNode updateTeller(JsonNode teller) throws UnirestException {
        String id = (String) teller.getObject().remove("id");
        return Helper.performServerPut("/fineract-provider/api/v1/tellers/" + id, teller);
    }

    public static JsonNode deleteTeller(String id) throws UnirestException {
        return Helper.performServerDelete("/fineract-provider/api/v1/tellers/" + id);
    }

}
