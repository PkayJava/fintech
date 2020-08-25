package com.angkorteam.fintech.dto.builder.client.group;

import java.io.Serializable;

import io.github.openunirest.http.JsonNode;

public class GroupNoteBuilder implements Serializable {

    private String groupId;
    private boolean hasGroupId;

    public GroupNoteBuilder withGroupId(String groupId) {
        this.groupId = groupId;
        this.hasGroupId = true;
        return this;
    }

    private String note;
    private boolean hasNote;

    public GroupNoteBuilder withNote(String note) {
        this.note = note;
        this.hasNote = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasGroupId) {
            object.getObject().put("groupId", this.groupId);
        }

        if (this.hasNote) {
            object.getObject().put("note", this.note);
        }

        return object;
    }

}
