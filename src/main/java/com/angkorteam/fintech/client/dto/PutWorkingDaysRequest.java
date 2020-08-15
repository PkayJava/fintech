package com.angkorteam.fintech.client.dto;

import com.angkorteam.fintech.client.enums.RepaymentOption;

public class PutWorkingDaysRequest {

    /**
     * FREQ=WEEKLY;INTERVAL=1;BYDAY=MO,TU,WE,TH,FR
     */
    private String recurrence;

    private RepaymentOption repaymentRescheduleType;

    private boolean extendTermForDailyRepayments;

    private boolean extendTermForRepaymentsOnHolidays;

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public RepaymentOption getRepaymentRescheduleType() {
        return repaymentRescheduleType;
    }

    public void setRepaymentRescheduleType(RepaymentOption repaymentRescheduleType) {
        this.repaymentRescheduleType = repaymentRescheduleType;
    }

    public boolean isExtendTermForDailyRepayments() {
        return extendTermForDailyRepayments;
    }

    public void setExtendTermForDailyRepayments(boolean extendTermForDailyRepayments) {
        this.extendTermForDailyRepayments = extendTermForDailyRepayments;
    }

    public boolean isExtendTermForRepaymentsOnHolidays() {
        return extendTermForRepaymentsOnHolidays;
    }

    public void setExtendTermForRepaymentsOnHolidays(boolean extendTermForRepaymentsOnHolidays) {
        this.extendTermForRepaymentsOnHolidays = extendTermForRepaymentsOnHolidays;
    }
}
