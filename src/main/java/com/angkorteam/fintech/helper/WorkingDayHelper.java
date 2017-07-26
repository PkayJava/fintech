package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class WorkingDayHelper {

    public static JsonNode update(IMifos session, JsonNode object) throws UnirestException {
        return Helper.performServerPut(session, "/api/v1/workingdays", object);
    }

}
