package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class WorkingDayHelper {

    public static JsonNode update(IMifos session, JsonNode object) {
        return Helper.performServerPut(session, "/api/v1/workingdays", object);
    }

}
