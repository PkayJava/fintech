package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/28/17.
 */
public class GLAccountHelper {

    public static JsonNode create(IMifos session, JsonNode object) throws UnirestException {
        return Helper.performServerPost(session, "/api/v1/glaccounts", object);
    }

    public static JsonNode disable(IMifos session, String id) throws UnirestException {
        JsonNode node = new com.angkorteam.fintech.dto.JsonNode();
        node.getObject().put("disabled", true);
        return Helper.performServerPut(session, "/api/v1/glaccounts/" + id, node);
    }

    public static JsonNode enable(IMifos session, String id) throws UnirestException {
        JsonNode node = new com.angkorteam.fintech.dto.JsonNode();
        node.getObject().put("disabled", false);
        return Helper.performServerPut(session, "/api/v1/glaccounts/" + id, node);
    }

    public static JsonNode delete(IMifos session, String id) throws UnirestException {
        return Helper.performServerDelete(session, "/api/v1/glaccounts/" + id);
    }

    public static JsonNode update(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPut(session, "/api/v1/glaccounts/" + id, object);
    }

    public static JsonNode postEntry(IMifos session, JsonNode object) throws UnirestException {
        return Helper.performServerPost(session, "/api/v1/journalentries", object);
    }

    public static JsonNode reverseEntry(IMifos session, String id, String reason) throws UnirestException {
        JsonNode node = new com.angkorteam.fintech.dto.JsonNode();
        node.getObject().put("transactionId", id);
        node.getObject().put("comments", reason);
        return Helper.performServerPost(session, "/api/v1/journalentries/" + id + "?command=reverse", node);
    }

}
