package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 7/18/17.
 */
public class TaxComponentHelper {

    public static JsonNode createTaxComponent(JsonNode taxComponent) throws UnirestException {
        return Helper.performServerPost("/fineract-provider/api/v1/taxes/component", taxComponent);
    }

}
