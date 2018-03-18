package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import com.angkorteam.fintech.dto.builder.StaffBuilder;
import com.mashape.unirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class StaffHelper {

    private static final String TRANSFER_STAFF_URL = "/api/v1/groups";

    private static final String CREATE_STAFF_URL = "/api/v1/staff";

    public static JsonNode create(IMifos session, JsonNode object) {
        return Helper.performServerPost(session, CREATE_STAFF_URL, object);
    }

    public static JsonNode update(IMifos session, JsonNode object) {
        String id = (String) object.getObject().remove("id");
        final String url = CREATE_STAFF_URL + "/" + id;
        return Helper.performServerPut(session, url, object);
    }

    public static JsonNode transferToGroup(IMifos session, final String groupId, final String staffIdToTransfer, final String note) {
        final String url = TRANSFER_STAFF_URL + "/" + groupId + "?command=transferStaff";
        StaffBuilder builder = new StaffBuilder();
        builder.withStaffId(staffIdToTransfer);
        builder.withNote(note);
        return Helper.performServerPost(session, url, builder.build());
    }

}
