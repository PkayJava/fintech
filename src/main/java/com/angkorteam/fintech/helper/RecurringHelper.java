package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import io.github.openunirest.http.JsonNode;

public class RecurringHelper {

    public static JsonNode create(IMifos session, JsonNode object) {
        return Helper.performServerPost(session, "/api/v1/recurringdepositproducts", object);
    }

}
