package com.angkorteam.fintech.client.dto;

import java.util.Date;

public class PostOfficeRequest {

    /**
     * limit to 100
     */
    private String name;

    private Date openingDate;

    private long parentId;

    /**
     * limit to 100
     */
    private String externalId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

}
