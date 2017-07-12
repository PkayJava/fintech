package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public class AccrualHelper {

    public static JsonNode submit(JsonNode accrual) throws UnirestException {
        return Helper.performServerPost("/fineract-provider/api/v1/runaccruals", accrual);
    }

}
