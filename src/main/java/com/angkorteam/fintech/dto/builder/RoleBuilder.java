package com.angkorteam.fintech.dto.builder;

import com.mashape.unirest.http.JsonNode;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class RoleBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private String name;
    private boolean hasName;

    private String description;
    private boolean hasDescription;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        if (this.hasName) {
            object.getObject().put("name", this.name);
        }
        if (this.hasDescription) {
            object.getObject().put("description", this.description);
        }
        return object;
    }

    public RoleBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    public RoleBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    public RoleBuilder withDescription(String description) {
        this.description = description;
        this.hasDescription = true;
        return this;
    }

}
