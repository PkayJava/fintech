package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/28/17.
 */
public class GLAccountHelper {

    public static JsonNode create(IMifos session, JsonNode object) {
        return Helper.performServerPost(session, "/api/v1/glaccounts", object);
    }

    public static JsonNode disable(IMifos session, String id) {
        JsonNode node = new com.angkorteam.fintech.dto.JsonNode();
        node.getObject().put("disabled", true);
        return Helper.performServerPut(session, "/api/v1/glaccounts/" + id, node);
    }

    public static JsonNode enable(IMifos session, String id) {
        JsonNode node = new com.angkorteam.fintech.dto.JsonNode();
        node.getObject().put("disabled", false);
        return Helper.performServerPut(session, "/api/v1/glaccounts/" + id, node);
    }

    public static JsonNode delete(IMifos session, String id) {
        return Helper.performServerDelete(session, "/api/v1/glaccounts/" + id);
    }

    public static JsonNode update(IMifos session, JsonNode object) {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPut(session, "/api/v1/glaccounts/" + id, object);
    }

    public static JsonNode postEntry(IMifos session, JsonNode object) {
        return Helper.performServerPost(session, "/api/v1/journalentries", object);
    }

    public static JsonNode reverseEntry(IMifos session, String id, String reason) {
        JsonNode node = new com.angkorteam.fintech.dto.JsonNode();
        node.getObject().put("transactionId", id);
        node.getObject().put("comments", reason);
        return Helper.performServerPost(session, "/api/v1/journalentries/" + id + "?command=reverse", node);
    }

}
