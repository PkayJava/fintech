package com.angkorteam.fintech.dto.builder.client.client;

import java.io.Serializable;

import org.json.JSONObject;

import com.mashape.unirest.http.JsonNode;

public class ClientChargeWaiveBuilder implements Serializable {

    private String clientId;
    private boolean hasClientId;

    public ClientChargeWaiveBuilder withClientId(String clientId) {
        this.clientId = clientId;
        this.hasClientId = true;
        return this;
    }

    private String chargeId;
    private boolean hasChargeId;

    public ClientChargeWaiveBuilder withChargeId(String chargeId) {
        this.chargeId = chargeId;
        this.hasChargeId = true;
        return this;
    }

    private String resourceType;
    private boolean hasResourceType;

    public ClientChargeWaiveBuilder withResourceType(String resourceType) {
        this.resourceType = resourceType;
        this.hasResourceType = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());

        if (this.hasChargeId) {
            object.getObject().put("chargeId", this.chargeId);
        }

        if (this.hasResourceType) {
            object.getObject().put("resourceType", this.resourceType);
        }

        if (this.hasClientId) {
            object.getObject().put("clientId", this.clientId);
        }

        return object;
    }

}
