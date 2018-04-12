package com.angkorteam.fintech.dto.builder.saving;

import io.github.openunirest.http.JsonNode;

public class UndoApproveBuilder {

    private String id;
    private boolean hasId;

    public UndoApproveBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    private String note;
    private boolean hasNote;

    public UndoApproveBuilder withNote(String note) {
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
