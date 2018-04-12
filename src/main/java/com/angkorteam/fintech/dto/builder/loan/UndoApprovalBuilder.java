package com.angkorteam.fintech.dto.builder.loan;

import io.github.openunirest.http.JsonNode;

public class UndoApprovalBuilder {

    private String id;
    private boolean hasId;

    public UndoApprovalBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    private String note;
    private boolean hasNote;

    public UndoApprovalBuilder withNote(String note) {
        this.note = note;
        this.hasNote = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasNote) {
            object.getObject().put("note", this.note);
        }

        if (this.hasId) {
            object.getObject().put("id", this.id);
        }

        return object;
    }

}
