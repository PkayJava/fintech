package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class HolidayHelper {

    public static JsonNode create(IMifos session, JsonNode object) throws UnirestException {
        return Helper.performServerPost(session, "/api/v1/holidays", object);
    }

    public static JsonNode activate(IMifos session, String id) throws UnirestException {
        return Helper.performServerPost(session, "/api/v1/holidays/" + id + "?command=activate");
    }
}
