package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/28/17.
 */
public class GLAccountHelper {

    private final static String CREATE_GL_ACCOUNT_URL = "/fineract-provider/api/v1/glaccounts";

    private final static String UPDATE_GL_ACCOUNT_URL = "/fineract-provider/api/v1/glaccounts";

    private final static String CREATE_ENTRY_URL = "/fineract-provider/api/v1/journalentries";

    public static JsonNode createGLAccount(JsonNode glAccount) throws UnirestException {
        return Helper.performServerPost(CREATE_GL_ACCOUNT_URL, glAccount);
    }

    public static JsonNode disableGLAccount(String id) throws UnirestException {
        JsonNode node = new com.angkorteam.fintech.dto.JsonNode();
        node.getObject().put("disabled", true);
        return Helper.performServerPut("/fineract-provider/api/v1/glaccounts/" + id, node);
    }

    public static JsonNode enableGLAccount(String id) throws UnirestException {
        JsonNode node = new com.angkorteam.fintech.dto.JsonNode();
        node.getObject().put("disabled", false);
        return Helper.performServerPut("/fineract-provider/api/v1/glaccounts/" + id, node);
    }

    public static JsonNode deleteGLAccount(String id) throws UnirestException {
        return Helper.performServerDelete("/fineract-provider/api/v1/glaccounts/" + id);
    }

    public static JsonNode updateGLAccount(JsonNode glAccount) throws UnirestException {
        String id = (String) glAccount.getObject().remove("id");
        return Helper.performServerPut(UPDATE_GL_ACCOUNT_URL + "/" + id, glAccount);
    }

    public static JsonNode createEntry(JsonNode entry) throws UnirestException {
        return Helper.performServerPost(CREATE_ENTRY_URL, entry);
    }

    public static JsonNode reverseEntry(String transactionId, String reason) throws UnirestException {
        JsonNode node = new com.angkorteam.fintech.dto.JsonNode();
        node.getObject().put("transactionId", transactionId);
        node.getObject().put("comments", reason);
        return Helper.performServerPost("/fineract-provider/api/v1/journalentries/" + transactionId + "?command=reverse", node);
    }

}
