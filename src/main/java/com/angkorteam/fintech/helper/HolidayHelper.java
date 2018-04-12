package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class HolidayHelper {

    public static JsonNode create(IMifos session, JsonNode object) {
        return Helper.performServerPost(session, "/api/v1/holidays", object);
    }

    public static JsonNode activate(IMifos session, String id) {
        return Helper.performServerPost(session, "/api/v1/holidays/" + id + "?command=activate");
    }
}
