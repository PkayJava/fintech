package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import com.angkorteam.fintech.dto.request.StaffBuilder;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class StaffHelper {

    private static final String TRANSFER_STAFF_URL = "/api/v1/groups";

    private static final String CREATE_STAFF_URL = "/api/v1/staff";

    public static JsonNode create(IMifos session, JsonNode object) throws UnirestException {
        return Helper.performServerPost(session, CREATE_STAFF_URL, object);
    }

    public static JsonNode update(IMifos session, JsonNode object) throws UnirestException {
        String id = (String) object.getObject().remove("id");
        final String url = CREATE_STAFF_URL + "/" + id;
        return Helper.performServerPut(session, url, object);
    }

    public static JsonNode transferToGroup(IMifos session, final String groupId, final String staffIdToTransfer,
                                           final String note) throws UnirestException {
        final String url = TRANSFER_STAFF_URL + "/" + groupId + "?command=transferStaff";
        StaffBuilder builder = new StaffBuilder();
        builder.withStaffId(staffIdToTransfer);
        builder.withNote(note);
        return Helper.performServerPost(session, url, builder.build());
    }

}
