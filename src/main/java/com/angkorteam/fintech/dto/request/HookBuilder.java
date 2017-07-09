package com.angkorteam.fintech.dto.request;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 6/27/17.
 */
public class HookBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private String template;
    private boolean hasTemplate;

    private String name;
    private boolean hasName;

    private boolean active;
    private boolean hasActive;

    private Map<String, String> config = Maps.newHashMap();
    private boolean hasConfig;

    private List<Map<String, String>> events = Lists.newArrayList();
    private boolean hasEvents;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasEvents) {
            object.getObject().put("events", this.events);
        }
        if (this.hasConfig) {
            object.getObject().put("config", config);
        }
        if (this.hasActive) {
            object.getObject().put("isActive", this.active);
        }
        if (this.hasName) {
            object.getObject().put("displayName", this.name);
        }
        if (this.hasTemplate) {
            object.getObject().put("name", this.template);
        }
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        return object;
    }

    public HookBuilder withEvent(String entityName, String actionName) {
        Map<String, String> event = Maps.newHashMap();
        event.put("entityName", entityName);
        event.put("actionName", actionName);
        this.events.add(event);
        this.hasEvents = true;
        return this;
    }

    public HookBuilder withConfig(String name, String value) {
        this.config.put(name, value);
        this.hasConfig = true;
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

    public HookBuilder withTemplate(String template) {
        this.template = template;
        this.hasTemplate = true;
        return this;
    }

    public HookBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

}
