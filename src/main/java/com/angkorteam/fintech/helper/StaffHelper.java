package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.dto.request.StaffBuilder;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class StaffHelper {

    private static final String TRANSFER_STAFF_URL = "/fineract-provider/api/v1/groups";

    private static final String CREATE_STAFF_URL = "/fineract-provider/api/v1/staff";

    public static JsonNode createStaff(JsonNode staff) throws UnirestException {
        final String url = CREATE_STAFF_URL;
        return Helper.performServerPost(url, staff);
    }

    public static JsonNode updateStaff(JsonNode staff) throws UnirestException {
        String id = (String) staff.getObject().remove("id");
        final String url = CREATE_STAFF_URL + "/" + id;
        return Helper.performServerPut(url, staff);
    }

    public static JsonNode transferStaffToGroup(final String groupId, final String staffIdToTransfer, final String note) throws UnirestException {
        final String url = TRANSFER_STAFF_URL + "/" + groupId + "?command=transferStaff";
        StaffBuilder builder = new StaffBuilder();
        builder.withStaffId(staffIdToTransfer);
        builder.withNote(note);
        return Helper.performServerPost(url, builder.build());
    }

}
