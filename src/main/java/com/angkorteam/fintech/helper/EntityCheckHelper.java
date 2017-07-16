package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 7/15/17.
 */
public class EntityCheckHelper {


    public static JsonNode createChecker(JsonNode entityChecker) throws UnirestException {
        return Helper.performServerPost("/fineract-provider/api/v1/entityDatatableChecks", entityChecker);
    }

    public static JsonNode deleteChecker(String id) throws UnirestException {
        return Helper.performServerDelete("/fineract-provider/api/v1/entityDatatableChecks/" + id);
    }

}
