package com.angkorteam.fintech.dto.request;

import java.io.Serializable;

import com.mashape.unirest.http.JsonNode;

public class ClientIdentifierBuilder implements Serializable {

    private String documentTypeId;
    private boolean hasDocumentTypeId;

    private String documentKey;
    private boolean hasDocumentKey;

    private String description;
    private boolean hasDescription;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasDocumentTypeId) {
            object.getObject().put("documentTypeId", this.documentTypeId);
        }
        if (this.hasDocumentKey) {
            object.getObject().put("documentKey", this.documentKey);
        }
        if (this.hasDescription) {
            object.getObject().put("description", this.description);
        }
        return object;
    }

    public ClientIdentifierBuilder withDescription(String description) {
        this.description = description;
        this.hasDescription = true;
        return this;
    }

    public ClientIdentifierBuilder withDocumentKey(String documentKey) {
        this.documentKey = documentKey;
        this.hasDocumentKey = true;
        return this;
    }

    public ClientIdentifierBuilder withDocumentTypeId(String documentTypeId) {
        this.documentTypeId = documentTypeId;
        this.hasDocumentTypeId = true;
        return this;
    }

}
