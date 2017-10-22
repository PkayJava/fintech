package com.angkorteam.fintech.dto.builder.client.client;

import java.io.Serializable;

import org.json.JSONObject;

import com.mashape.unirest.http.JsonNode;

public class ClientDefaulSavingAccountBuilder implements Serializable {

    private String id;
    private boolean hasId;

    public ClientDefaulSavingAccountBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    private String savingsAccountId;
    private boolean hasSavingsAccountId;

    public ClientDefaulSavingAccountBuilder withSavingsAccountId(String savingsAccountId) {
        this.savingsAccountId = savingsAccountId;
        this.hasSavingsAccountId = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());

        if (this.hasSavingsAccountId) {
            object.getObject().put("savingsAccountId", this.savingsAccountId);
        }

        if (this.hasId) {
            object.getObject().put("id", this.id);
        }

        return object;
    }

}
