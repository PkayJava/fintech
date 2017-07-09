package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class WorkingDayHelper {

    private static final String WORKINGDAYS_URL = "/fineract-provider/api/v1/workingdays";

    public static JsonNode updateWorkingDay(JsonNode workingDay) throws UnirestException {
        final String UPDATE_WORKINGDAYS_URL = WORKINGDAYS_URL;
        return Helper.performServerPut(UPDATE_WORKINGDAYS_URL, workingDay);
    }

}
