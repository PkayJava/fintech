package com.angkorteam.fintech.client.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostCenterRequest {


    // id - no used
    private long id;

    /**
     * officeId - mandatory
     */
    private long officeId;

    /**
     * name - mandatory
     */
    private String name;

    /**
     * active
     */
    private boolean active;

    /**
     * externalId
     */
    private String externalId;

    /**
     * activationDate - mandatory
     */
    private Date activationDate;

    /**
     * staffId
     */
    private long staffId;

    /**
     * submittedOnDate
     */
    private Date submittedOnDate;

    /**
     * groupMembers
     */
    private List<Long> groupMembers = new ArrayList<>();

    /**
     * datatables - no used
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

    public List<Long> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<Long> groupMembers) {
        this.groupMembers = groupMembers;
    }

}
