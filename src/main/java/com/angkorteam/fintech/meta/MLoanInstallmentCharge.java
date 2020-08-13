package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanInstallmentCharge extends AbstractTable {

    public final Column ID;

    public final Column LOAN_CHARGE_ID;

    public final Column LOAN_SCHEDULE_ID;

    public final Column DUE_DATE;

    public final Column AMOUNT;

    public final Column AMOUNT_PAID_DERIVED;

    public final Column AMOUNT_WAIVED_DERIVED;

    public final Column AMOUNT_WRITTEN_OFF_DERIVED;

    public final Column AMOUNT_OUTSTANDING_DERIVED;

    public final Column PAID_DERIVED;

    public final Column WAIVED;

    public final Column AMOUNT_THROUGH_CHARGE_PAYMENT;

    public static MLoanInstallmentCharge staticInitialize(DataContext dataContext) {
        return new MLoanInstallmentCharge(dataContext);
    }

    private MLoanInstallmentCharge(DataContext dataContext) {
        super(dataContext, "m_loan_installment_charge");
        this.ID = this.delegate.getColumnByName("id");
        this.LOAN_CHARGE_ID = this.delegate.getColumnByName("loan_charge_id");
        this.LOAN_SCHEDULE_ID = this.delegate.getColumnByName("loan_schedule_id");
        this.DUE_DATE = this.delegate.getColumnByName("due_date");
        this.AMOUNT = this.delegate.getColumnByName("amount");
        this.AMOUNT_PAID_DERIVED = this.delegate.getColumnByName("amount_paid_derived");
        this.AMOUNT_WAIVED_DERIVED = this.delegate.getColumnByName("amount_waived_derived");
        this.AMOUNT_WRITTEN_OFF_DERIVED = this.delegate.getColumnByName("amount_writtenoff_derived");
        this.AMOUNT_OUTSTANDING_DERIVED = this.delegate.getColumnByName("amount_outstanding_derived");
        this.PAID_DERIVED = this.delegate.getColumnByName("is_paid_derived");
        this.WAIVED = this.delegate.getColumnByName("waived");
        this.AMOUNT_THROUGH_CHARGE_PAYMENT = this.delegate.getColumnByName("amount_through_charge_payment");
    }

}
