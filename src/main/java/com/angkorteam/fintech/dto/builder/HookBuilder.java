package com.angkorteam.fintech.dto.builder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class HookBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private String name;
    private boolean hasName;

    private boolean active;
    private boolean hasActive;

    private String displayName;
    private boolean hasDisplayName;

    private String templateId;
    private boolean hasTemplateId;

    private List<Map<String, Object>> events = Lists.newArrayList();
    private boolean hasEvents;

    private Map<String, Object> config = Maps.newHashMap();
    private boolean hasConfig;

    public HookBuilder withTemplateId(String templateId) {
        this.templateId = templateId;
        this.hasTemplateId = true;
        return this;
    }

    public HookBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    public HookBuilder withDisplayName(String displayName) {
        this.displayName = displayName;
        this.hasDisplayName = true;
        return this;
    }

    public HookBuilder withActive(boolean active) {
        this.active = active;
        this.hasActive = true;
        return this;
    }

    public HookBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    public HookBuilder withConfig(String key, Object value) {
        this.config.put(key, value);
        this.hasConfig = true;
        return this;
    }

    public HookBuilder withEvent(String entity, String action) {
        this.hasEvents = true;
        Map<String, Object> event = Maps.newHashMap();
        event.put("actionName", action);
        event.put("entityName", entity);
        this.events.add(event);
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        if (this.hasConfig) {
            object.getObject().put("config", this.config);
        }
        if (this.hasEvents) {
            object.getObject().put("events", this.events);
        }
        if (this.hasTemplateId) {
            object.getObject().put("templateId", this.templateId);
        }
        if (this.hasDisplayName) {
            object.getObject().put("displayName", this.displayName);
        }
        if (this.hasName) {
            object.getObject().put("name", this.name);
        }
        if (this.hasActive) {
            object.getObject().put("isActive", this.active);
        }
        return object;
    }

}
