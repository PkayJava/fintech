package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanTransaction extends AbstractTable {

    public final Column ID;

    public final Column LOAN_ID;

    public final Column OFFICE_ID;

    public final Column PAYMENT_DETAIL_ID;

    public final Column REVERSED;

    public final Column EXTERNAL_ID;

    public final Column TRANSACTION_TYPE_ENUM;

    public final Column TRANSACTION_DATE;

    public final Column AMOUNT;

    public final Column PRINCIPAL_PORTION_DERIVED;

    public final Column INTEREST_PORTION_DERIVED;

    public final Column FEE_CHARGE_PORTION_DERIVED;

    public final Column PENALTY_CHARGE_PORTION_DERIVED;

    public final Column OVERPAYMENT_PORTION_DERIVED;

    public final Column UNRECOGNIZED_INCOME_PORTION;

    public final Column OUTSTANDING_LOAN_BALANCE_DERIVED;

    public final Column SUBMITTED_ON_DATE;

    public final Column MANUALLY_ADJUSTED_OR_REVERSED;

    public final Column CREATED_DATE;

    public final Column APP_USER_ID;

    public static MLoanTransaction staticInitialize(DataContext dataContext) {
        return new MLoanTransaction(dataContext);
    }

    private MLoanTransaction(DataContext dataContext) {
        super(dataContext, "m_loan_transaction");
        this.ID = this.delegate.getColumnByName("id");
        this.LOAN_ID = this.delegate.getColumnByName("loan_id");
        this.OFFICE_ID = this.delegate.getColumnByName("office_id");
        this.PAYMENT_DETAIL_ID = this.delegate.getColumnByName("payment_detail_id");
        this.REVERSED = this.delegate.getColumnByName("is_reversed");
        this.EXTERNAL_ID = this.delegate.getColumnByName("external_id");
        this.TRANSACTION_TYPE_ENUM = this.delegate.getColumnByName("transaction_type_enum");
        this.TRANSACTION_DATE = this.delegate.getColumnByName("transaction_date");
        this.AMOUNT = this.delegate.getColumnByName("amount");
        this.PRINCIPAL_PORTION_DERIVED = this.delegate.getColumnByName("principal_portion_derived");
        this.INTEREST_PORTION_DERIVED = this.delegate.getColumnByName("interest_portion_derived");
        this.FEE_CHARGE_PORTION_DERIVED = this.delegate.getColumnByName("fee_charges_portion_derived");
        this.PENALTY_CHARGE_PORTION_DERIVED = this.delegate.getColumnByName("penalty_charges_portion_derived");
        this.OVERPAYMENT_PORTION_DERIVED = this.delegate.getColumnByName("overpayment_portion_derived");
        this.UNRECOGNIZED_INCOME_PORTION = this.delegate.getColumnByName("unrecognized_income_portion");
        this.OUTSTANDING_LOAN_BALANCE_DERIVED = this.delegate.getColumnByName("outstanding_loan_balance_derived");
        this.SUBMITTED_ON_DATE = this.delegate.getColumnByName("submitted_on_date");
        this.MANUALLY_ADJUSTED_OR_REVERSED = this.delegate.getColumnByName("manually_adjusted_or_reversed");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
        this.APP_USER_ID = this.delegate.getColumnByName("appuser_id");
    }

}
