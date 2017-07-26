package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public class AccountingClosureHelper {

    public static JsonNode create(IMifos session, JsonNode object) throws UnirestException {
        return Helper.performServerPost(session, "/api/v1/glclosures", object);
    }

    public static JsonNode delete(IMifos session, String id) throws UnirestException {
        return Helper.performServerDelete(session, "/api/v1/glclosures/" + id);
    }

}
