package com.angkorteam.fintech.client.dto;

import com.google.gson.annotations.SerializedName;

public class PutCodeValueRequest {

    private String name;

    private long position;

    private String description;

    @SerializedName("isActive")
    private boolean active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
}
