package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MSavingAccountTransaction extends AbstractTable {

    public final Column ID;

    public final Column SAVING_ACCOUNT_ID;

    public final Column OFFICE_ID;

    public final Column PAYMENT_DETAIL_ID;

    public final Column TRANSACTION_TYPE_ENUM;

    public final Column REVERSED;

    public final Column TRANSACTION_DATE;

    public final Column AMOUNT;

    public final Column OVERDRAFT_AMOUNT_DERIVED;

    public final Column BALANCE_END_DATE_DERIVED;

    public final Column BALANCE_NUMBER_OF_DAY_DERIVED;

    public final Column RUNNING_BALANCE_DERIVED;

    public final Column CUMULATIVE_BALANCE_DERIVED;

    public final Column CREATED_DATE;

    public final Column APP_USER_ID;

    public final Column MANUAL;

    public final Column RELEASE_ID_OF_HOLD_AMOUNT;

    public final Column LOAN_DISBURSEMENT;

    public static MSavingAccountTransaction staticInitialize(DataContext dataContext) {
        return new MSavingAccountTransaction(dataContext);
    }

    private MSavingAccountTransaction(DataContext dataContext) {
        super(dataContext, "m_savings_account_transaction");
        this.ID = this.delegate.getColumnByName("id");
        this.SAVING_ACCOUNT_ID = this.delegate.getColumnByName("savings_account_id");
        this.OFFICE_ID = this.delegate.getColumnByName("office_id");
        this.PAYMENT_DETAIL_ID = this.delegate.getColumnByName("payment_detail_id");
        this.TRANSACTION_TYPE_ENUM = this.delegate.getColumnByName("transaction_type_enum");
        this.REVERSED = this.delegate.getColumnByName("is_reversed");
        this.TRANSACTION_DATE = this.delegate.getColumnByName("transaction_date");
        this.AMOUNT = this.delegate.getColumnByName("amount");
        this.OVERDRAFT_AMOUNT_DERIVED = this.delegate.getColumnByName("overdraft_amount_derived");
        this.BALANCE_END_DATE_DERIVED = this.delegate.getColumnByName("balance_end_date_derived");
        this.BALANCE_NUMBER_OF_DAY_DERIVED = this.delegate.getColumnByName("balance_number_of_days_derived");
        this.RUNNING_BALANCE_DERIVED = this.delegate.getColumnByName("running_balance_derived");
        this.CUMULATIVE_BALANCE_DERIVED = this.delegate.getColumnByName("cumulative_balance_derived");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
        this.APP_USER_ID = this.delegate.getColumnByName("appuser_id");
        this.MANUAL = this.delegate.getColumnByName("is_manual");
        this.RELEASE_ID_OF_HOLD_AMOUNT = this.delegate.getColumnByName("release_id_of_hold_amount");
        this.LOAN_DISBURSEMENT = this.delegate.getColumnByName("is_loan_disbursement");
    }

}
