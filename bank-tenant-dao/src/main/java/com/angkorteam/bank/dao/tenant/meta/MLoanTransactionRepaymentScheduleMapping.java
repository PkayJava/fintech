package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.LOAN_TRANSACTION_ID = getInternalColumnByName("loan_transaction_id");
        this.LOAN_REPAYMENT_SCHEDULE_ID = getInternalColumnByName("loan_repayment_schedule_id");
        this.AMOUNT = getInternalColumnByName("amount");
        this.PRINCIPAL_PORTION_DERIVED = getInternalColumnByName("principal_portion_derived");
        this.INTEREST_PORTION_DERIVED = getInternalColumnByName("interest_portion_derived");
        this.FEE_CHARGE_PORTION_DERIVED = getInternalColumnByName("fee_charges_portion_derived");
        this.PENALTY_CHARGE_PORTION_DERIVED = getInternalColumnByName("penalty_charges_portion_derived");
    }

}
