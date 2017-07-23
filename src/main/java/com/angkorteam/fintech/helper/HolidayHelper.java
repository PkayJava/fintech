package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class HolidayHelper {

    private static final String HOLIDAYS_URL = "/fineract-provider/api/v1/holidays";
    private static final String CREATE_HOLIDAY_URL = HOLIDAYS_URL;

    public static JsonNode create(JsonNode holiday) throws UnirestException {
        return Helper.performServerPost(CREATE_HOLIDAY_URL, holiday);
    }

    public static JsonNode activate(final String id) throws UnirestException {
        final String ACTIVATE_HOLIDAY_URL = HOLIDAYS_URL + "/" + id + "?command=activate";
        return Helper.performServerPost(ACTIVATE_HOLIDAY_URL);
    }
}
