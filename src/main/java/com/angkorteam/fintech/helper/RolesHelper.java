package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.Map;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class RolesHelper {

    private static final String CREATE_ROLE_URL = "/fineract-provider/api/v1/roles";
    private static final String MAKER_CHECKER_URL = "/fineract-provider/api/v1/permissions";
    private static final String ROLE_URL = "/fineract-provider/api/v1/roles";
    private static final String DISABLE_ROLE_COMMAND = "disable";
    private static final String ENABLE_ROLE_COMMAND = "enable";

    public static JsonNode createRole(final JsonNode role) throws UnirestException {
        return Helper.performServerPost(CREATE_ROLE_URL, role);
    }

    public static JsonNode disableRole(final String id) throws UnirestException {
        return Helper.performServerPost(createRoleOperationURL(DISABLE_ROLE_COMMAND, id));
    }

    public static JsonNode enableRole(final String id) throws UnirestException {
        return Helper.performServerPost(createRoleOperationURL(ENABLE_ROLE_COMMAND, id));
    }

    public static JsonNode deleteRole(final String id) throws UnirestException {
        return Helper.performServerDelete(createRoleOperationURL(ENABLE_ROLE_COMMAND, id));
    }

    public static JsonNode assignPermission(String id, Map<String, Boolean> permissions) throws UnirestException {
        JsonNode node = new com.angkorteam.fintech.dto.JsonNode();
        node.getObject().put("permissions", permissions);
        return Helper.performServerPut(ROLE_URL + "/" + id + "/permissions", node);
    }

    private static String createRoleOperationURL(final String command, final String roleId) {
        return ROLE_URL + "/" + roleId + "?command=" + command;
    }

    public static JsonNode makerCheckerPermission(Map<String, Boolean> permissions) throws UnirestException {
        JsonNode node = new com.angkorteam.fintech.dto.JsonNode();
        node.getObject().put("permissions", permissions);
        return Helper.performServerPut(MAKER_CHECKER_URL, node);
    }

}
