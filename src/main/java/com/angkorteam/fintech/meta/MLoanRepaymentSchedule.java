package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanRepaymentSchedule extends AbstractTable {

    public final Column ID;

    public final Column LOAN_ID;

    public final Column FROM_DATE;

    public final Column DUE_DATE;

    public final Column INSTALLMENT;

    public final Column PRINCIPAL_AMOUNT;

    public final Column PRINCIPAL_COMPLETED_DERIVED;

    public final Column PRINCIPAL_WRITTEN_OFF_DERIVED;

    public final Column INTEREST_AMOUNT;

    public final Column INTEREST_COMPLETED_DERIVED;

    public final Column INTEREST_WRITTEN_OFF_DERIVED;

    public final Column INTEREST_WAIVED_DERIVED;

    public final Column ACCRUAL_INTEREST_DERIVED;

    public final Column FEE_CHARGE_AMOUNT;

    public final Column FEE_CHARGE_COMPLETED_DERIVED;

    public final Column FEE_CHARGE_WRITTEN_OFF_DERIVED;

    public final Column FEE_CHARGE_WAIVED_DERIVED;

    public final Column ACCRUAL_FEE_CHARGE_DERIVED;

    public final Column PENALTY_CHARGE_AMOUNT;

    public final Column PENALTY_CHARGE_COMPLETED_DERIVED;

    public final Column PENALTY_CHARGE_WRITTEN_OFF_DERIVED;

    public final Column PENALTY_CHARGE_WAIVED_DERIVED;

    public final Column ACCRUAL_PENALTY_CHARGE_DERIVED;

    public final Column TOTAL_PAID_IN_ADVANCE_DERIVED;

    public final Column TOTAL_PAID_LATE_DERIVED;

    public final Column COMPLETED_DERIVED;

    public final Column OBLIGATIONS_MET_ON_DATE;

    public final Column CREATED_BY_ID;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_DATE;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column RECALCULATED_INTEREST_COMPONENT;

    public static MLoanRepaymentSchedule staticInitialize(DataContext dataContext) {
        return new MLoanRepaymentSchedule(dataContext);
    }

    private MLoanRepaymentSchedule(DataContext dataContext) {
        super(dataContext, "m_loan_repayment_schedule");
        this.ID = this.delegate.getColumnByName("id");
        this.LOAN_ID = this.delegate.getColumnByName("loan_id");
        this.FROM_DATE = this.delegate.getColumnByName("fromdate");
        this.DUE_DATE = this.delegate.getColumnByName("duedate");
        this.INSTALLMENT = this.delegate.getColumnByName("installment");
        this.PRINCIPAL_AMOUNT = this.delegate.getColumnByName("principal_amount");
        this.PRINCIPAL_COMPLETED_DERIVED = this.delegate.getColumnByName("principal_completed_derived");
        this.PRINCIPAL_WRITTEN_OFF_DERIVED = this.delegate.getColumnByName("principal_writtenoff_derived");
        this.INTEREST_AMOUNT = this.delegate.getColumnByName("interest_amount");
        this.INTEREST_COMPLETED_DERIVED = this.delegate.getColumnByName("interest_completed_derived");
        this.INTEREST_WRITTEN_OFF_DERIVED = this.delegate.getColumnByName("interest_writtenoff_derived");
        this.INTEREST_WAIVED_DERIVED = this.delegate.getColumnByName("interest_waived_derived");
        this.ACCRUAL_INTEREST_DERIVED = this.delegate.getColumnByName("accrual_interest_derived");
        this.FEE_CHARGE_AMOUNT = this.delegate.getColumnByName("fee_charges_amount");
        this.FEE_CHARGE_COMPLETED_DERIVED = this.delegate.getColumnByName("fee_charges_completed_derived");
        this.FEE_CHARGE_WRITTEN_OFF_DERIVED = this.delegate.getColumnByName("fee_charges_writtenoff_derived");
        this.FEE_CHARGE_WAIVED_DERIVED = this.delegate.getColumnByName("fee_charges_waived_derived");
        this.ACCRUAL_FEE_CHARGE_DERIVED = this.delegate.getColumnByName("accrual_fee_charges_derived");
        this.PENALTY_CHARGE_AMOUNT = this.delegate.getColumnByName("penalty_charges_amount");
        this.PENALTY_CHARGE_COMPLETED_DERIVED = this.delegate.getColumnByName("penalty_charges_completed_derived");
        this.PENALTY_CHARGE_WRITTEN_OFF_DERIVED = this.delegate.getColumnByName("penalty_charges_writtenoff_derived");
        this.PENALTY_CHARGE_WAIVED_DERIVED = this.delegate.getColumnByName("penalty_charges_waived_derived");
        this.ACCRUAL_PENALTY_CHARGE_DERIVED = this.delegate.getColumnByName("accrual_penalty_charges_derived");
        this.TOTAL_PAID_IN_ADVANCE_DERIVED = this.delegate.getColumnByName("total_paid_in_advance_derived");
        this.TOTAL_PAID_LATE_DERIVED = this.delegate.getColumnByName("total_paid_late_derived");
        this.COMPLETED_DERIVED = this.delegate.getColumnByName("completed_derived");
        this.OBLIGATIONS_MET_ON_DATE = this.delegate.getColumnByName("obligations_met_on_date");
        this.CREATED_BY_ID = this.delegate.getColumnByName("createdby_id");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
        this.LAST_MODIFIED_DATE = this.delegate.getColumnByName("lastmodified_date");
        this.LAST_MODIFIED_BY_ID = this.delegate.getColumnByName("lastmodifiedby_id");
        this.RECALCULATED_INTEREST_COMPONENT = this.delegate.getColumnByName("recalculated_interest_component");
    }

}
