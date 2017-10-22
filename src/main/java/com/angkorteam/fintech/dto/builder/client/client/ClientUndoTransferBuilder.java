package com.angkorteam.fintech.dto.builder.client.client;

import java.io.Serializable;

import com.mashape.unirest.http.JsonNode;

public class ClientUndoTransferBuilder implements Serializable {

    private String id;
    private boolean hasId;

    public ClientUndoTransferBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    private String note;
    private boolean hasNote;

    public ClientUndoTransferBuilder withNote(String note) {
        this.note = note;
        this.hasNote = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasId) {
            object.getObject().put("id", this.id);
        }

        if (this.hasNote) {
            object.getObject().put("note", this.note);
        }

        return object;
    }

}
