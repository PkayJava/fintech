package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ClientHelper {

    public static JsonNode createClient(IMifos session, JsonNode object) throws UnirestException {
        return Helper.performServerPost(session, "/api/v1/clients", object);
    }

    public static JsonNode createGroup(IMifos session, JsonNode object) throws UnirestException {
        return Helper.performServerPost(session, "/api/v1/groups", object);
    }

    public static JsonNode createCenter(IMifos session, JsonNode object) throws UnirestException {
        return Helper.performServerPost(session, "/api/v1/centers", object);
    }

    public static JsonNode updateCenter(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPut(session, "/api/v1/centers/" + id, object);
    }

    public static JsonNode postCenterNote(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("centerId");
        return Helper.performServerPost(session, "/api/v1/groups/" + id + "/notes", object);
    }

    public static JsonNode postGroupNote(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("groupId");
        return Helper.performServerPost(session, "/api/v1/groups/" + id + "/notes", object);
    }

    public static JsonNode approveCenterAccount(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPost(session, "/api/v1/savingsaccounts/" + id + "?command=approve", object);
    }

    public static JsonNode undoApproveCenterAccount(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPost(session, "/api/v1/savingsaccounts/" + id + "?command=undoapproval", object);
    }

    public static JsonNode activateCenterAccount(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPost(session, "/api/v1/savingsaccounts/" + id + "?command=activate", object);
    }

    public static JsonNode depositCenterAccount(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPost(session, "/api/v1/savingsaccounts/" + id + "/transactions?command=deposit", object);
    }
    
    public static JsonNode withdrawCenterAccount(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPost(session, "/api/v1/savingsaccounts/" + id + "/transactions?command=withdrawal", object);
    }

    // http://fineract:8080/fineract-provider/api/v1/savingsaccounts/7?command=close

}
