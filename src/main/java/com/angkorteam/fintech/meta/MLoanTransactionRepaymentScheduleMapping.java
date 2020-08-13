package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanTransactionRepaymentScheduleMapping extends AbstractTable {

    public final Column ID;

    public final Column LOAN_TRANSACTION_ID;

    public final Column LOAN_REPAYMENT_SCHEDULE_ID;

    public final Column AMOUNT;

    public final Column PRINCIPAL_PORTION_DERIVED;

    public final Column INTEREST_PORTION_DERIVED;

    public final Column FEE_CHARGE_PORTION_DERIVED;

    public final Column PENALTY_CHARGE_PORTION_DERIVED;

    public static MLoanTransactionRepaymentScheduleMapping staticInitialize(DataContext dataContext) {
        return new MLoanTransactionRepaymentScheduleMapping(dataContext);
    }

    private MLoanTransactionRepaymentScheduleMapping(DataContext dataContext) {
        super(dataContext, "m_loan_transaction_repayment_schedule_mapping");
        this.ID = this.delegate.getColumnByName("id");
        this.LOAN_TRANSACTION_ID = this.delegate.getColumnByName("loan_transaction_id");
        this.LOAN_REPAYMENT_SCHEDULE_ID = this.delegate.getColumnByName("loan_repayment_schedule_id");
        this.AMOUNT = this.delegate.getColumnByName("amount");
        this.PRINCIPAL_PORTION_DERIVED = this.delegate.getColumnByName("principal_portion_derived");
        this.INTEREST_PORTION_DERIVED = this.delegate.getColumnByName("interest_portion_derived");
        this.FEE_CHARGE_PORTION_DERIVED = this.delegate.getColumnByName("fee_charges_portion_derived");
        this.PENALTY_CHARGE_PORTION_DERIVED = this.delegate.getColumnByName("penalty_charges_portion_derived");
    }

}
