package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.Map;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class RoleHelper {

    private static final String DISABLE_ROLE_COMMAND = "disable";
    private static final String ENABLE_ROLE_COMMAND = "enable";
    private static final String ROLE_URL = "/api/v1/roles";

    public static JsonNode create(IMifos session, JsonNode object) throws UnirestException {
        return Helper.performServerPost(session, "/api/v1/roles", object);
    }

    public static JsonNode disable(IMifos session, String id) throws UnirestException {
        return Helper.performServerPost(session, createRoleOperationURL(DISABLE_ROLE_COMMAND, id));
    }

    public static JsonNode enable(IMifos session, String id) throws UnirestException {
        return Helper.performServerPost(session, createRoleOperationURL(ENABLE_ROLE_COMMAND, id));
    }

    public static JsonNode delete(IMifos session, String id) throws UnirestException {
        return Helper.performServerDelete(session, createRoleOperationURL(ENABLE_ROLE_COMMAND, id));
    }

    public static JsonNode assignPermission(IMifos session, String id, Map<String, Boolean> permissions) throws UnirestException {
        JsonNode node = new com.angkorteam.fintech.dto.JsonNode();
        node.getObject().put("permissions", permissions);
        return Helper.performServerPut(session, "/api/v1/roles/" + id + "/permissions", node);
    }

    private static String createRoleOperationURL(String command, final String roleId) {
        return ROLE_URL + "/" + roleId + "?command=" + command;
    }

    public static JsonNode makerCheckerPermission(IMifos session, Map<String, Boolean> permissions) throws UnirestException {
        JsonNode node = new com.angkorteam.fintech.dto.JsonNode();
        node.getObject().put("permissions", permissions);
        return Helper.performServerPut(session, "/api/v1/permissions", node);
    }

}
