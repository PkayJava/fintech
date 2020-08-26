package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.SAVING_ACCOUNT_ID = getInternalColumnByName("savings_account_id");
        this.OFFICE_ID = getInternalColumnByName("office_id");
        this.PAYMENT_DETAIL_ID = getInternalColumnByName("payment_detail_id");
        this.TRANSACTION_TYPE_ENUM = getInternalColumnByName("transaction_type_enum");
        this.REVERSED = getInternalColumnByName("is_reversed");
        this.TRANSACTION_DATE = getInternalColumnByName("transaction_date");
        this.AMOUNT = getInternalColumnByName("amount");
        this.OVERDRAFT_AMOUNT_DERIVED = getInternalColumnByName("overdraft_amount_derived");
        this.BALANCE_END_DATE_DERIVED = getInternalColumnByName("balance_end_date_derived");
        this.BALANCE_NUMBER_OF_DAY_DERIVED = getInternalColumnByName("balance_number_of_days_derived");
        this.RUNNING_BALANCE_DERIVED = getInternalColumnByName("running_balance_derived");
        this.CUMULATIVE_BALANCE_DERIVED = getInternalColumnByName("cumulative_balance_derived");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.APP_USER_ID = getInternalColumnByName("appuser_id");
        this.MANUAL = getInternalColumnByName("is_manual");
        this.RELEASE_ID_OF_HOLD_AMOUNT = getInternalColumnByName("release_id_of_hold_amount");
        this.LOAN_DISBURSEMENT = getInternalColumnByName("is_loan_disbursement");
    }

}
