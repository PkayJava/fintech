package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public class AccountingClosureHelper {

    public static JsonNode createClosure(JsonNode closure) throws UnirestException {
        return Helper.performServerPost("/fineract-provider/api/v1/glclosures", closure);
    }

    public static JsonNode deleteClosure(String id) throws UnirestException {
        return Helper.performServerDelete("/fineract-provider/api/v1/glclosures/" + id);
    }

}
