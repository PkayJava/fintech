package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class AccGlJournalEntry extends AbstractTable {

    public final Column ID;

    public final Column ACCOUNT_ID;

    public final Column OFFICE_ID;

    public final Column REVERSAL_ID;

    public final Column CURRENCY_CODE;

    public final Column TRANSACTION_ID;

    public final Column LOAN_TRANSACTION_ID;

    public final Column SAVING_TRANSACTION_ID;

    public final Column CLIENT_TRANSACTION_ID;

    public final Column REVERSED;

    public final Column REF_NUM;

    public final Column MANUAL_ENTRY;

    public final Column ENTRY_DATE;

    public final Column TYPE_ENUM;

    public final Column AMOUNT;

    public final Column DESCRIPTION;

    public final Column ENTITY_TYPE_ENUM;

    public final Column ENTITY_ID;

    public final Column CREATED_BY_ID;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_DATE;

    public final Column RUNNING_BALANCE_CALCULATED;

    public final Column OFFICE_RUNNING_BALANCE;

    public final Column ORGANIZATION_RUNNING_BALANCE;

    public final Column PAYMENT_DETAIL_ID;

    public final Column SHARE_TRANSACTION_ID;

    public final Column TRANSACTION_DATE;

    public static AccGlJournalEntry staticInitialize(DataContext dataContext) {
        return new AccGlJournalEntry(dataContext);
    }

    private AccGlJournalEntry(DataContext dataContext) {
        super(dataContext, "acc_gl_financial_activity_account");
        this.ID = this.delegate.getColumnByName("id");
        this.ACCOUNT_ID = this.delegate.getColumnByName("account_id");
        this.OFFICE_ID = this.delegate.getColumnByName("office_id");
        this.REVERSAL_ID = this.delegate.getColumnByName("reversal_id");
        this.CURRENCY_CODE = this.delegate.getColumnByName("currency_code");
        this.TRANSACTION_ID = this.delegate.getColumnByName("transaction_id");
        this.LOAN_TRANSACTION_ID = this.delegate.getColumnByName("loan_transaction_id");
        this.SAVING_TRANSACTION_ID = this.delegate.getColumnByName("savings_transaction_id");
        this.CLIENT_TRANSACTION_ID = this.delegate.getColumnByName("client_transaction_id");
        this.REVERSED = this.delegate.getColumnByName("reversed");
        this.REF_NUM = this.delegate.getColumnByName("ref_num");
        this.MANUAL_ENTRY = this.delegate.getColumnByName("manual_entry");
        this.ENTRY_DATE = this.delegate.getColumnByName("entry_date");
        this.TYPE_ENUM = this.delegate.getColumnByName("type_enum");
        this.AMOUNT = this.delegate.getColumnByName("amount");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
        this.ENTITY_TYPE_ENUM = this.delegate.getColumnByName("entity_type_enum");
        this.ENTITY_ID = this.delegate.getColumnByName("entity_id");
        this.CREATED_BY_ID = this.delegate.getColumnByName("createdby_id");
        this.LAST_MODIFIED_BY_ID = this.delegate.getColumnByName("lastmodifiedby_id");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
        this.LAST_MODIFIED_DATE = this.delegate.getColumnByName("lastmodified_date");
        this.RUNNING_BALANCE_CALCULATED = this.delegate.getColumnByName("is_running_balance_calculated");
        this.OFFICE_RUNNING_BALANCE = this.delegate.getColumnByName("office_running_balance");
        this.ORGANIZATION_RUNNING_BALANCE = this.delegate.getColumnByName("organization_running_balance");
        this.PAYMENT_DETAIL_ID = this.delegate.getColumnByName("payment_details_id");
        this.SHARE_TRANSACTION_ID = this.delegate.getColumnByName("share_transaction_id");
        this.TRANSACTION_DATE = this.delegate.getColumnByName("transaction_date");
    }

}
