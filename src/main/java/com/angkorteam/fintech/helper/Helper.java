package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.Application;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class Helper {

    public static JsonNode performServerPost(String url, JsonNode body) throws UnirestException {
        if (url.contains("?")) {
            return Unirest.post(Application.MIFOS + url + "&" + Application.TENANT_IDENTIFIER)
                    .header("Authorization", "Basic bWlmb3M6cGFzc3dvcmQ\u003d")
                    .header("Content-Type", "application/json")
                    .body(body).asJson().getBody();
        } else {
            return Unirest.post(Application.MIFOS + url + "?" + Application.TENANT_IDENTIFIER)
                    .header("Authorization", "Basic bWlmb3M6cGFzc3dvcmQ\u003d")
                    .header("Content-Type", "application/json")
                    .body(body).asJson().getBody();
        }
    }

    public static JsonNode performServerPost(String url) throws UnirestException {
        if (url.contains("?")) {
            return Unirest.post(Application.MIFOS + url + "&" + Application.TENANT_IDENTIFIER)
                    .header("Authorization", "Basic bWlmb3M6cGFzc3dvcmQ\u003d")
                    .header("Content-Type", "application/json")
                    .asJson().getBody();
        } else {
            return Unirest.post(Application.MIFOS + url + "?" + Application.TENANT_IDENTIFIER)
                    .header("Authorization", "Basic bWlmb3M6cGFzc3dvcmQ\u003d")
                    .header("Content-Type", "application/json")
                    .asJson().getBody();
        }
    }

    public static JsonNode performServerDelete(String url) throws UnirestException {
        if (url.contains("?")) {
            return Unirest.delete(Application.MIFOS + url + "&" + Application.TENANT_IDENTIFIER)
                    .header("Authorization", "Basic bWlmb3M6cGFzc3dvcmQ\u003d")
                    .header("Content-Type", "application/json")
                    .asJson().getBody();
        } else {
            return Unirest.delete(Application.MIFOS + url + "?" + Application.TENANT_IDENTIFIER)
                    .header("Authorization", "Basic bWlmb3M6cGFzc3dvcmQ\u003d")
                    .header("Content-Type", "application/json")
                    .asJson().getBody();
        }
    }

    public static JsonNode performServerPut(String url, JsonNode body) throws UnirestException {
        if (url.contains("?")) {
            return Unirest.put(Application.MIFOS + url + "&" + Application.TENANT_IDENTIFIER)
                    .header("Authorization", "Basic bWlmb3M6cGFzc3dvcmQ\u003d")
                    .header("Content-Type", "application/json")
                    .body(body).asJson().getBody();
        } else {
            return Unirest.put(Application.MIFOS + url + "?" + Application.TENANT_IDENTIFIER)
                    .header("Authorization", "Basic bWlmb3M6cGFzc3dvcmQ\u003d")
                    .header("Content-Type", "application/json")
                    .body(body).asJson().getBody();
        }
    }

}
