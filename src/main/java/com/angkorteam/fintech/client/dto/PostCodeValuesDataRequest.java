package com.angkorteam.fintech.client.dto;

import com.google.gson.annotations.SerializedName;

public class PostCodeValuesDataRequest {

    private String name;

    private long position;

    private String description;

    @SerializedName("isActive")
    private boolean active;

    @SerializedName("isMandatory")
    private boolean mandatory;

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

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }
    
}
