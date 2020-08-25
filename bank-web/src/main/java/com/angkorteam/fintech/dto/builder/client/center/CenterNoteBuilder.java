package com.angkorteam.fintech.dto.builder.client.center;

import java.io.Serializable;

import io.github.openunirest.http.JsonNode;

public class CenterNoteBuilder implements Serializable {

    private String centerId;
    private boolean hasCenterId;

    public CenterNoteBuilder withCenterId(String centerId) {
        this.centerId = centerId;
        this.hasCenterId = true;
        return this;
    }

    private String note;
    private boolean hasNote;

    public CenterNoteBuilder withNote(String note) {
        this.note = note;
        this.hasNote = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasCenterId) {
            object.getObject().put("centerId", this.centerId);
        }

        if (this.hasNote) {
            object.getObject().put("note", this.note);
        }

        return object;
    }

}
