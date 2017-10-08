package com.angkorteam.fintech.dto.builder;

import com.mashape.unirest.http.JsonNode;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class FundBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private String name;
    private boolean hasName;

    private String externalId;
    private boolean hasExternalId;

    public FundBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    public FundBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    public FundBuilder withExternalId(String externalId) {
        this.externalId = externalId;
        this.hasExternalId = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        if (this.hasName) {
            object.getObject().put("name", this.name);
        }
        if (this.hasExternalId) {
            object.getObject().put("externalId", this.externalId);
        }
        return object;
    }

}
