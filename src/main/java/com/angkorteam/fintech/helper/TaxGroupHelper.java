package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 7/20/17.
 */
public class TaxGroupHelper {

    public static JsonNode createTaxGroup(JsonNode taxGroup) throws UnirestException {
        return Helper.performServerPost("/fineract-provider/api/v1/taxes/group", taxGroup);
    }

    public static JsonNode updateTaxGroup(JsonNode taxGroup) throws UnirestException {
        String id = (String) taxGroup.getObject().remove("id");
        return Helper.performServerPut("/fineract-provider/api/v1/taxes/group/" + id, taxGroup);
    }

}
