package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;

import org.json.JSONObject;

import com.mashape.unirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/27/17.
 */
public class CodeValueBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private String codeId;
    private boolean hasCodeId;

    private String name;
    private boolean hasName;

    private String description;
    private boolean hasDescription;

    private Long position;
    private boolean hasPosition;

    private boolean active;
    private boolean hasActive;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        if (this.hasDescription) {
            object.getObject().put("description", this.description);
        }
        if (this.hasPosition) {
            object.getObject().put("position", this.position);
        }
        if (this.hasName) {
            object.getObject().put("name", this.name);
        }
        if (this.hasActive) {
            object.getObject().put("isActive", this.active);
        }
        if (this.hasCodeId) {
            object.getObject().put("codeId", this.codeId);
        }
        return object;
    }

    public CodeValueBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    public CodeValueBuilder withCodeId(String codeId) {
        this.codeId = codeId;
        this.hasCodeId = true;
        return this;
    }

    public CodeValueBuilder withActive(boolean active) {
        this.active = active;
        this.hasActive = true;
        return this;
    }

    public CodeValueBuilder withPosition(Long position) {
        this.position = position;
        this.hasPosition = true;
        return this;
    }

    public CodeValueBuilder withDescription(String description) {
        this.description = description;
        this.hasDescription = true;
        return this;
    }

    public CodeValueBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }
}
