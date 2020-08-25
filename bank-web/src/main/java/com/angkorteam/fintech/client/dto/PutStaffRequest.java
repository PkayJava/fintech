package com.angkorteam.fintech.client.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PutStaffRequest {

    private long officeId;

    @SerializedName("firstname")
    private String firstName;

    @SerializedName("lastname")
    private String lastName;

    private String externalId;

    private String mobileNo;

    @SerializedName("isLoanOfficer")
    private boolean loanOfficer;

    @SerializedName("isActive")
    private boolean active;

    private Date joiningDate;

    public long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(long officeId) {
        this.officeId = officeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public boolean isLoanOfficer() {
        return loanOfficer;
    }

    public void setLoanOfficer(boolean loanOfficer) {
        this.loanOfficer = loanOfficer;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

}
