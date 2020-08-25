package com.angkorteam.fintech.client.dto;

import com.angkorteam.fintech.client.enums.AccountUsage;
import com.angkorteam.fintech.client.renums.GLAccountType;

public class PutGLAccountsRequest {

    private String name;

    private boolean disabled;

    private long parentId;

    private long tagId;

    private AccountUsage usage;

    private GLAccountType type;

    private boolean manualEntriesAllowed;

    private String glCode;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public AccountUsage getUsage() {
        return usage;
    }

    public void setUsage(AccountUsage usage) {
        this.usage = usage;
    }

    public GLAccountType getType() {
        return type;
    }

    public void setType(GLAccountType type) {
        this.type = type;
    }

    public boolean isManualEntriesAllowed() {
        return manualEntriesAllowed;
    }

    public void setManualEntriesAllowed(boolean manualEntriesAllowed) {
        this.manualEntriesAllowed = manualEntriesAllowed;
    }

    public String getGlCode() {
        return glCode;
    }

    public void setGlCode(String glCode) {
        this.glCode = glCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
