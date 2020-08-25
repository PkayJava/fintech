package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;

import io.github.openunirest.http.JsonNode;

public class JobBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private String displayName;
    private boolean hasDisplayName;

    private String cronExpression;
    private boolean hasCronExpression;

    private boolean active;
    private boolean hasActive;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasActive) {
            object.getObject().put("active", this.active);
        }
        if (this.hasCronExpression) {
            object.getObject().put("cronExpression", this.cronExpression);
        }
        if (this.hasDisplayName) {
            object.getObject().put("displayName", this.displayName);
        }
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        return object;
    }

    public JobBuilder withActive(boolean active) {
        this.active = active;
        this.hasActive = true;
        return this;
    }

    public JobBuilder withCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
        this.hasCronExpression = true;
        return this;
    }

    public JobBuilder withDisplayName(String displayName) {
        this.displayName = displayName;
        this.hasDisplayName = true;
        return this;
    }

    public JobBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

}
