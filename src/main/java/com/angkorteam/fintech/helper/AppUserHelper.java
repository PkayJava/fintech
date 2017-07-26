package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class AppUserHelper {

    public static JsonNode create(JsonNode object) throws UnirestException {
	return Helper.performServerPost("/fineract-provider/api/v1/users", object);
    }

    public static JsonNode update(JsonNode object) throws UnirestException {
	String id = (String) object.getObject().remove("id");
	return Helper.performServerPut("/fineract-provider/api/v1/users/" + id, object);
    }

    public static JsonNode delete(String id) throws UnirestException {
	return Helper.performServerDelete("/fineract-provider/api/v1/users/" + id);
    }

}
