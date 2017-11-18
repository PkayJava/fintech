package com.angkorteam.fintech.dto.builder.client.client;

import java.io.Serializable;

import org.json.JSONObject;

import com.mashape.unirest.http.JsonNode;

public class ClientNoteBuilder implements Serializable {

    private String note;
    private boolean hasNote;

    private String clientId;
    private boolean hasClientId;

    public ClientNoteBuilder withNote(String note) {
        this.note = note;
        this.hasNote = true;
        return this;
    }

    public ClientNoteBuilder withClientId(String clientId) {
        this.clientId = clientId;
        this.hasClientId = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());

        if (this.hasNote) {
            object.getObject().put("note", this.note);
        }

        if (this.hasClientId) {
            object.getObject().put("clientId", this.clientId);
        }

        return object;
    }
}
