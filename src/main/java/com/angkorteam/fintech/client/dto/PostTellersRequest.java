package com.angkorteam.fintech.client.dto;

import com.angkorteam.fintech.client.renums.TellerStatus;

import java.util.Date;

public class PostTellersRequest {

    private long officeId;

    private String name;

    private String description;

    private Date startDate;

    private Date endDate;

    private TellerStatus status;

    public long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(long officeId) {
        this.officeId = officeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TellerStatus getStatus() {
        return status;
    }

    public void setStatus(TellerStatus status) {
        this.status = status;
    }

}
