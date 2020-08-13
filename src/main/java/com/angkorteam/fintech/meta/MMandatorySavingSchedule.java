package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MMandatorySavingSchedule extends AbstractTable {

    public final Column ID;

    public final Column SAVING_ACCOUNT_ID;

    public final Column FROM_DATE;

    public final Column DUE_DATE;

    public final Column INSTALLMENT;

    public final Column DEPOSIT_AMOUNT;

    public final Column DEPOSIT_AMOUNT_COMPLETED_DERIVED;

    public final Column TOTAL_PAID_IN_ADVANCE_DERIVED;

    public final Column TOTAL_PAID_LATE_DERIVED;

    public final Column COMPLETED_DERIVED;

    public final Column OBLIGATION_MET_ON_DATE;

    public final Column CREATED_BY_ID;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_DATE;

    public final Column LAST_MODIFIED_BY_ID;

    public static MMandatorySavingSchedule staticInitialize(DataContext dataContext) {
        return new MMandatorySavingSchedule(dataContext);
    }

    private MMandatorySavingSchedule(DataContext dataContext) {
        super(dataContext, "m_mandatory_savings_schedule");
        this.ID = this.delegate.getColumnByName("id");
        this.SAVING_ACCOUNT_ID = this.delegate.getColumnByName("savings_account_id");
        this.FROM_DATE = this.delegate.getColumnByName("fromdate");
        this.DUE_DATE = this.delegate.getColumnByName("duedate");
        this.INSTALLMENT = this.delegate.getColumnByName("installment");
        this.DEPOSIT_AMOUNT = this.delegate.getColumnByName("deposit_amount");
        this.DEPOSIT_AMOUNT_COMPLETED_DERIVED = this.delegate.getColumnByName("deposit_amount_completed_derived");
        this.TOTAL_PAID_IN_ADVANCE_DERIVED = this.delegate.getColumnByName("total_paid_in_advance_derived");
        this.TOTAL_PAID_LATE_DERIVED = this.delegate.getColumnByName("total_paid_late_derived");
        this.COMPLETED_DERIVED = this.delegate.getColumnByName("completed_derived");
        this.OBLIGATION_MET_ON_DATE = this.delegate.getColumnByName("obligations_met_on_date");
        this.CREATED_BY_ID = this.delegate.getColumnByName("createdby_id");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
        this.LAST_MODIFIED_DATE = this.delegate.getColumnByName("lastmodified_date");
        this.LAST_MODIFIED_BY_ID = this.delegate.getColumnByName("lastmodifiedby_id");
    }

}
