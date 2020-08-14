package com.angkorteam.fintech.client.dto;

import java.util.Date;

public class PostRunAccrualRequest {

    /**
     * required
     */
    private Date tillDate;

    public Date getTillDate() {
        return tillDate;
    }

    public void setTillDate(Date tillDate) {
        this.tillDate = tillDate;
    }

}
