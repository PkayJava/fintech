package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public class AccrualHelper {

    public static JsonNode submit(IMifos session, JsonNode object) throws UnirestException {
        return Helper.performServerPost(session, "/api/v1/runaccruals", object);
    }

}
