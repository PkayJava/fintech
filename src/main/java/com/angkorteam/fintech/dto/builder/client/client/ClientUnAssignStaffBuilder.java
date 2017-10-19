package com.angkorteam.fintech.dto.builder.client.client;

import java.io.Serializable;

import org.json.JSONObject;

import com.mashape.unirest.http.JsonNode;

public class ClientUnAssignStaffBuilder implements Serializable {

    private String staffId;
    private boolean hasStaffId;

    public ClientUnAssignStaffBuilder withStaffId(String staffId) {
        this.staffId = staffId;
        this.hasStaffId = true;
        return this;
    }

    private String id;
    private boolean hasId;

    public ClientUnAssignStaffBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());

        if (this.hasStaffId) {
            object.getObject().put("staffId", this.staffId);
        }

        if (this.hasId) {
            object.getObject().put("id", this.id);
        }

        return object;
    }

}
