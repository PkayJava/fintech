package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.LOAN_CHARGE_ID = getInternalColumnByName("loan_charge_id");
        this.LOAN_SCHEDULE_ID = getInternalColumnByName("loan_schedule_id");
        this.DUE_DATE = getInternalColumnByName("due_date");
        this.AMOUNT = getInternalColumnByName("amount");
        this.AMOUNT_PAID_DERIVED = getInternalColumnByName("amount_paid_derived");
        this.AMOUNT_WAIVED_DERIVED = getInternalColumnByName("amount_waived_derived");
        this.AMOUNT_WRITTEN_OFF_DERIVED = getInternalColumnByName("amount_writtenoff_derived");
        this.AMOUNT_OUTSTANDING_DERIVED = getInternalColumnByName("amount_outstanding_derived");
        this.PAID_DERIVED = getInternalColumnByName("is_paid_derived");
        this.WAIVED = getInternalColumnByName("waived");
        this.AMOUNT_THROUGH_CHARGE_PAYMENT = getInternalColumnByName("amount_through_charge_payment");
    }

}
