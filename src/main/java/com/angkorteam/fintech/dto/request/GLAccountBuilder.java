package com.angkorteam.fintech.dto.request;

import com.mashape.unirest.http.JsonNode;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 6/27/17.
 */
public class GLAccountBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private String glCode;
    private boolean hasGlCode;

    private String name;
    private boolean hasName;

    private boolean manualEntriesAllowed;
    private boolean hasManualEntriesAllowed;

    private Account type;
    private boolean hasType;

    private Usage usage;
    private boolean hasUsage;

    private String description;
    private boolean hasDescription;

    private String parentId;
    private boolean hasParentId;

    private String tagId;
    private boolean hasTagId;

    public GLAccountBuilder withTagId(String tagId) {
        this.tagId = tagId;
        this.hasTagId = true;
        return this;
    }

    public GLAccountBuilder withParentId(String parentId) {
        this.parentId = parentId;
        this.hasParentId = true;
        return this;
    }

    public GLAccountBuilder withDescription(String description) {
        this.description = description;
        this.hasDescription = true;
        return this;
    }

    public GLAccountBuilder withUsage(Usage usage) {
        this.usage = usage;
        this.hasUsage = true;
        return this;
    }

    public GLAccountBuilder withType(Account type) {
        this.type = type;
        this.hasType = true;
        return this;
    }

    public GLAccountBuilder withManualEntriesAllowed(boolean manualEntriesAllowed) {
        this.manualEntriesAllowed = manualEntriesAllowed;
        this.hasManualEntriesAllowed = true;
        return this;
    }

    public GLAccountBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    public GLAccountBuilder withGlCode(String glCode) {
        this.glCode = glCode;
        this.hasGlCode = true;
        return this;
    }

    public GLAccountBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        if (this.hasTagId) {
            object.getObject().put("tagId", Integer.valueOf(this.tagId));
        }
        if (this.hasParentId) {
            object.getObject().put("parentId", Integer.valueOf(this.parentId));
        }
        if (this.hasName) {
            object.getObject().put("name", this.name);
        }
        if (this.hasGlCode) {
            object.getObject().put("glCode", this.glCode);
        }
        if (this.hasManualEntriesAllowed) {
            object.getObject().put("manualEntriesAllowed", Boolean.valueOf(this.manualEntriesAllowed));
        }
        if (this.hasType) {
            object.getObject().put("type", Integer.valueOf(this.type.getLiteral()));
        }
        if (this.hasUsage) {
            object.getObject().put("usage", Integer.valueOf(this.usage.getLiteral()));
        }
        if (this.hasDescription) {
            object.getObject().put("description", this.description);
        }
        return object;
    }

    public enum Account {
        Asset("1"),
        Liability("2"),
        Equity("3"),
        Income("4"),
        Expense("5");

        private String literal;

        Account(String literal) {
            this.literal = literal;
        }

        public String getLiteral() {
            return literal;
        }
    }

    public enum Usage {

        Detail("1"),
        Header("2");

        private String literal;

        Usage(String literal) {
            this.literal = literal;
        }

        public String getLiteral() {
            return literal;
        }

    }

}
