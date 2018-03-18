package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import com.mashape.unirest.http.JsonNode;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public class AccrualHelper {

    public static JsonNode submit(IMifos session, JsonNode object) {
        return Helper.performServerPost(session, "/api/v1/runaccruals", object);
    }

}
