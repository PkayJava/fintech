package com.angkorteam.fintech.client;

import java.util.Date;

public class PostGlClosureRequest {

    private long officeId;

    private Date closingDate;

    /**
     * limit to 500
     */
    private String comments;

    public long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(long officeId) {
        this.officeId = officeId;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
