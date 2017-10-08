package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;

import com.mashape.unirest.http.JsonNode;

public class NoteBuilder implements Serializable {

    private String centerId;
    private boolean hasCenterId;

    public NoteBuilder withCenterId(String centerId) {
        this.centerId = centerId;
        this.hasCenterId = true;
        return this;
    }

    private String groupId;
    private boolean hasGroupId;

    public NoteBuilder withGroupId(String groupId) {
        this.groupId = groupId;
        this.hasGroupId = true;
        return this;
    }

    private String note;
    private boolean hasNote;

    public NoteBuilder withNote(String note) {
        this.note = note;
        this.hasNote = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasCenterId) {
            object.getObject().put("centerId", this.centerId);
        }

        if (this.hasGroupId) {
            object.getObject().put("groupId", this.groupId);
        }

        if (this.hasNote) {
            object.getObject().put("note", this.note);
        }

        return object;
    }

}
