package com.angkorteam.fintech.client.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostGroupsRequest {

    private long officeId;

    private String name;

    private boolean active;

    private String accountNo;

    private String externalId;

    private Date activationDate;

    private long staffId;

    private Date submittedOnDate;

    private List<Long> clientMembers = new ArrayList<>();

    private List<Long> groupMembers = new ArrayList<>();

    private List<Datatable> datatables = new ArrayList<>();

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

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
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

    public List<Long> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<Long> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public List<Datatable> getDatatables() {
        return datatables;
    }

    public void setDatatables(List<Datatable> datatables) {
        this.datatables = datatables;
    }

    public static class Datatable {

        private String registeredTableName;

        private Data data;

        public String getRegisteredTableName() {
            return registeredTableName;
        }

        public void setRegisteredTableName(String registeredTableName) {
            this.registeredTableName = registeredTableName;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

    }

    public static class Data {

    }

}
