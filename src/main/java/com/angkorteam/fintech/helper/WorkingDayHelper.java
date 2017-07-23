package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class WorkingDayHelper {

    private static final String WORKING_DAYS_URL = "/fineract-provider/api/v1/workingdays";

    public static JsonNode update(JsonNode workingDay) throws UnirestException {
        return Helper.performServerPut(WORKING_DAYS_URL, workingDay);
    }

}
