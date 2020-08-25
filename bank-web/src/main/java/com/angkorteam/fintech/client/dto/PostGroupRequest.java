package com.angkorteam.fintech.client.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostGroupRequest {

    /**
     * id - no used
     */
    private long id;

    /**
     * name - mandatory
     */
    private String name;

    /**
     * externalId
     */
    private String externalId;

    /**
     * centerId
     */
    private long centerId;

    /**
     * officeId - mandatory
     */
    private long officeId;

    /**
     * staffId
     */
    private long staffId;

    /**
     * active
     */
    private boolean active;

    /**
     * activationDate - mandatory
     */
    private Date activationDate;

    /**
     * clientMembers
     */
    private List<Long> clientMembers = new ArrayList<>();

    /**
     * collectionMeetingCalendar - no used
     */
    private String collectionMeetingCalendar;

    /**
     * submittedOnDate
     */
    private Date submittedOnDate;

    /**
     * dataTables
     */
    private List<DataTable> dataTables = new ArrayList<>();

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    public long getStaffId() {
        return staffId;
    }

    public void setStaffId(long staffId) {
        this.staffId = staffId;
    }

    public Date getSubmittedOnDate() {
        return submittedOnDate;
    }

    public void setSubmittedOnDate(Date submittedOnDate) {
        this.submittedOnDate = submittedOnDate;
    }

    public List<Long> getClientMembers() {
        return clientMembers;
    }

    public void setClientMembers(List<Long> clientMembers) {
        this.clientMembers = clientMembers;
    }

    public List<DataTable> getDataTables() {
        return dataTables;
    }

    public void setDataTables(List<DataTable> dataTables) {
        this.dataTables = dataTables;
    }

    public long getCenterId() {
        return centerId;
    }

    public void setCenterId(long centerId) {
        this.centerId = centerId;
    }

}
