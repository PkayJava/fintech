package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanRepaymentScheduleHistory extends AbstractTable {

    public final Column ID;

    public final Column LOAN_ID;

    public final Column FROM_DATE;

    public final Column DUE_DATE;

    public final Column INSTALLMENT;

    public final Column PRINCIPAL_AMOUNT;

    public final Column INTEREST_AMOUNT;

    public final Column FEE_CHARGE_AMOUNT;

    public final Column PENALTY_CHARGE_AMOUNT;

    public final Column CREATED_BY_ID;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_DATE;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column VERSION;

    public static MLoanRepaymentScheduleHistory staticInitialize(DataContext dataContext) {
        return new MLoanRepaymentScheduleHistory(dataContext);
    }

    private MLoanRepaymentScheduleHistory(DataContext dataContext) {
        super(dataContext, "m_loan_repayment_schedule_history");
        this.ID = this.delegate.getColumnByName("id");
        this.LOAN_ID = this.delegate.getColumnByName("loan_id");
        this.FROM_DATE = this.delegate.getColumnByName("fromdate");
        this.DUE_DATE = this.delegate.getColumnByName("duedate");
        this.INSTALLMENT = this.delegate.getColumnByName("installment");
        this.PRINCIPAL_AMOUNT = this.delegate.getColumnByName("principal_amount");
        this.INTEREST_AMOUNT = this.delegate.getColumnByName("interest_amount");
        this.FEE_CHARGE_AMOUNT = this.delegate.getColumnByName("fee_charges_amount");
        this.PENALTY_CHARGE_AMOUNT = this.delegate.getColumnByName("penalty_charges_amount");
        this.CREATED_BY_ID = this.delegate.getColumnByName("createdby_id");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
        this.LAST_MODIFIED_DATE = this.delegate.getColumnByName("lastmodified_date");
        this.LAST_MODIFIED_BY_ID = this.delegate.getColumnByName("lastmodifiedby_id");
        this.VERSION = this.delegate.getColumnByName("version");
    }

}
