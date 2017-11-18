package com.angkorteam.fintech.dto.builder.client.client;

import java.io.Serializable;

import org.json.JSONObject;

import com.mashape.unirest.http.JsonNode;

public class ClientIdentityBuilder implements Serializable {

    private String documentTypeId;
    private boolean hasDocumentTypeId;

    public ClientIdentityBuilder withDocumentTypeId(String documentTypeId) {
        this.documentTypeId = documentTypeId;
        this.hasDocumentTypeId = true;
        return this;
    }

    private String clientId;
    private boolean hasClientId;

    public ClientIdentityBuilder withClientId(String clientId) {
        this.clientId = clientId;
        this.hasClientId = true;
        return this;
    }

    private String status;
    private boolean hasStatus;

    public ClientIdentityBuilder withStatus(String status) {
        this.status = status;
        this.hasStatus = true;
        return this;
    }

    private String documentKey;
    private boolean hasDocumentKey;

    public ClientIdentityBuilder withDocumentKey(String documentKey) {
        this.documentKey = documentKey;
        this.hasDocumentKey = true;
        return this;
    }

    private String description;
    private boolean hasDescription;

    public ClientIdentityBuilder withDescription(String description) {
        this.description = description;
        this.hasDescription = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());

        if (this.hasDescription) {
            object.getObject().put("description", this.description);
        }

        if (this.hasDocumentKey) {
            object.getObject().put("documentKey", this.documentKey);
        }

        if (this.hasStatus) {
            object.getObject().put("status", this.status);
        }

        if (this.hasDocumentTypeId) {
            object.getObject().put("documentTypeId", this.documentTypeId);
        }

        if (this.hasClientId) {
            object.getObject().put("clientId", this.clientId);
        }

        return object;
    }

}
