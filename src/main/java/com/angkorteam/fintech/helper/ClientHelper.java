package com.angkorteam.fintech.helper;

import java.io.InputStream;

import com.angkorteam.fintech.IMifos;
import com.mashape.unirest.http.HttpResponse;
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

    public static JsonNode unassignStaffClient(IMifos session, JsonNode object) throws UnirestException {
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

    public static HttpResponse<InputStream> retrieveClientImage(IMifos session, String clientId) throws UnirestException {
        return Helper.performServerGet(session, "/api/v1/clients/" + clientId + "/images?maxHeight=150");
    }

    public static JsonNode uploadClientImage(IMifos session, String clientId, String image) throws UnirestException {
        return Helper.performServerPost(session, "/api/v1/clients/" + clientId + "/images", image);
    }

    public static JsonNode transferClient(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPost(session, "/api/v1/clients/" + id + "?command=proposeTransfer", object);
    }

    public static JsonNode acceptTransferClient(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPost(session, "/api/v1/clients/" + id + "?command=acceptTransfer", object);
    }

    public static JsonNode rejectTransferClient(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPost(session, "/api/v1/clients/" + id + "?command=rejectTransfer", object);
    }

    public static JsonNode undoTransferClient(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPost(session, "/api/v1/clients/" + id + "?command=withdrawTransfer", object);
    }

    public static JsonNode defaultSavingAccountClient(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPost(session, "/api/v1/clients/" + id + "?command=updateSavingsAccount", object);
    }

    public static JsonNode createIdentityClient(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("clientId");
        return Helper.performServerPost(session, "/api/v1/clients/" + id + "/identifiers", object);
    }

}
