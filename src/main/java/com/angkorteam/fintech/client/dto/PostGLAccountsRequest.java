package com.angkorteam.fintech.client.dto;

import com.angkorteam.fintech.client.enums.GLAccountType;
import com.angkorteam.fintech.client.enums.AccountUsage;

public class PostGLAccountsRequest {

    private String name;

    private String glCode;

    private boolean manualEntriesAllowed;

    private GLAccountType type;

    private int tagId;

    private long parentId;

    private AccountUsage usage;

    private String description;

    private boolean disabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGlCode() {
        return glCode;
    }

    public void setGlCode(String glCode) {
        this.glCode = glCode;
    }

    public boolean isManualEntriesAllowed() {
        return manualEntriesAllowed;
    }

    public void setManualEntriesAllowed(boolean manualEntriesAllowed) {
        this.manualEntriesAllowed = manualEntriesAllowed;
    }

    public GLAccountType getType() {
        return type;
    }

    public void setType(GLAccountType type) {
        this.type = type;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public AccountUsage getUsage() {
        return usage;
    }

    public void setUsage(AccountUsage usage) {
        this.usage = usage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
