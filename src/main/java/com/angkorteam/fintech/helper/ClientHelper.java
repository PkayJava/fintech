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

    public static JsonNode approveSavingAccount(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPost(session, "/api/v1/savingsaccounts/" + id + "?command=approve", object);
    }

    public static JsonNode undoApproveSavingAccount(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPost(session, "/api/v1/savingsaccounts/" + id + "?command=undoapproval", object);
    }

    public static JsonNode activateSavingAccount(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPost(session, "/api/v1/savingsaccounts/" + id + "?command=activate", object);
    }

    public static JsonNode depositSavingAccount(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPost(session, "/api/v1/savingsaccounts/" + id + "/transactions?command=deposit", object);
    }

    public static JsonNode withdrawSavingAccount(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPost(session, "/api/v1/savingsaccounts/" + id + "/transactions?command=withdrawal", object);
    }

    public static JsonNode createSavingAccount(IMifos session, JsonNode object) throws UnirestException {
        return Helper.performServerPost(session, "/api/v1/savingsaccounts", object);
    }

    public static JsonNode closeSavingAccount(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPost(session, "/api/v1/savingsaccounts/" + id + "?command=close", object);
    }

    public static JsonNode assignStaffClient(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPost(session, "/api/v1/clients/" + id + "?command=assignStaff", object);
    }

    public static JsonNode UnassignStaffClient(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPost(session, "/api/v1/clients/" + id + "?command=unassignstaff", object);
    }

    public static JsonNode assignStaffSavingAccount(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPost(session, "/api/v1/savingsaccounts/" + id + "?command=assignSavingsOfficer", object);
    }

    public static JsonNode unassignStaffSavingAccount(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPost(session, "/api/v1/savingsaccounts/" + id + "?command=unassignSavingsOfficer", object);
    }
}
