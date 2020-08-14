package com.angkorteam.fintech.client.dto;

import com.angkorteam.fintech.dto.enums.ReschedulingType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostHolidaysRequest {

    private String name;

    private Date fromDate;

    private Date toDate;

    private ReschedulingType reschedulingType;

    private String description;

    private Date repaymentsRescheduledTo;

    private List<Office> offices = new ArrayList<>();

    public List<Office> getOffices() {
        return offices;
    }

    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    public Date getRepaymentsRescheduledTo() {
        return repaymentsRescheduledTo;
    }

    public void setRepaymentsRescheduledTo(Date repaymentsRescheduledTo) {
        this.repaymentsRescheduledTo = repaymentsRescheduledTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public ReschedulingType getReschedulingType() {
        return reschedulingType;
    }

    public void setReschedulingType(ReschedulingType reschedulingType) {
        this.reschedulingType = reschedulingType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class Office {

        private final long officeId;

        public Office(long officeId) {
            this.officeId = officeId;
        }

        public long getOfficeId() {
            return officeId;
        }

    }

}
