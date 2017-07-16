package com.angkorteam.fintech.dto.request;

import com.angkorteam.fintech.dto.EntityStatus;
import com.mashape.unirest.http.JsonNode;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 7/15/17.
 */
public class EntityCheckBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private String entity;
    private boolean hasEntity;

    private EntityStatus status;
    private boolean hasStatus;

    private String datatableName;
    private boolean hasDatatableName;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        if (this.hasEntity) {
            object.getObject().put("entity", this.entity);
        }
        if (this.hasStatus) {
            if (this.status != null) {
                object.getObject().put("status", Integer.valueOf(this.status.getLiteral()));
            } else {
                object.getObject().put("status", (String) null);
            }
        }
        if (this.hasDatatableName) {
            object.getObject().put("datatableName", this.datatableName);
        }
        return object;
    }

    public EntityCheckBuilder withDatatableName(String datatableName) {
        this.datatableName = datatableName;
        this.hasDatatableName = true;
        return this;
    }

    public EntityCheckBuilder withStatus(EntityStatus status) {
        this.status = status;
        this.hasStatus = true;
        return this;
    }

    public EntityCheckBuilder withEntity(String entity) {
        this.entity = entity;
        this.hasEntity = true;
        return this;
    }

    public EntityCheckBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

}
