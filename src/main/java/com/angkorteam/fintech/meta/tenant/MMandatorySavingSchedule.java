package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.SAVING_ACCOUNT_ID = getInternalColumnByName("savings_account_id");
        this.FROM_DATE = getInternalColumnByName("fromdate");
        this.DUE_DATE = getInternalColumnByName("duedate");
        this.INSTALLMENT = getInternalColumnByName("installment");
        this.DEPOSIT_AMOUNT = getInternalColumnByName("deposit_amount");
        this.DEPOSIT_AMOUNT_COMPLETED_DERIVED = getInternalColumnByName("deposit_amount_completed_derived");
        this.TOTAL_PAID_IN_ADVANCE_DERIVED = getInternalColumnByName("total_paid_in_advance_derived");
        this.TOTAL_PAID_LATE_DERIVED = getInternalColumnByName("total_paid_late_derived");
        this.COMPLETED_DERIVED = getInternalColumnByName("completed_derived");
        this.OBLIGATION_MET_ON_DATE = getInternalColumnByName("obligations_met_on_date");
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
    }

}
