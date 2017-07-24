package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ChargeHelper {

    public static JsonNode create(JsonNode object) throws UnirestException {
	return Helper.performServerPost("/fineract-provider/api/v1/charges", object);
    }

}
