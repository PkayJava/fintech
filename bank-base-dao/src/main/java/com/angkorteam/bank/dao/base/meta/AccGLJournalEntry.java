package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class AccGLJournalEntry extends AbstractTable {

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

    public static AccGLJournalEntry staticInitialize(DataContext dataContext) {
        return new AccGLJournalEntry(dataContext);
    }

    private AccGLJournalEntry(DataContext dataContext) {
        super(dataContext, "acc_gl_journal_entry");
        this.ID = getInternalColumnByName("id");
        this.ACCOUNT_ID = getInternalColumnByName("account_id");
        this.OFFICE_ID = getInternalColumnByName("office_id");
        this.REVERSAL_ID = getInternalColumnByName("reversal_id");
        this.CURRENCY_CODE = getInternalColumnByName("currency_code");
        this.TRANSACTION_ID = getInternalColumnByName("transaction_id");
        this.LOAN_TRANSACTION_ID = getInternalColumnByName("loan_transaction_id");
        this.SAVING_TRANSACTION_ID = getInternalColumnByName("savings_transaction_id");
        this.CLIENT_TRANSACTION_ID = getInternalColumnByName("client_transaction_id");
        this.REVERSED = getInternalColumnByName("reversed");
        this.REF_NUM = getInternalColumnByName("ref_num");
        this.MANUAL_ENTRY = getInternalColumnByName("manual_entry");
        this.ENTRY_DATE = getInternalColumnByName("entry_date");
        this.TYPE_ENUM = getInternalColumnByName("type_enum");
        this.AMOUNT = getInternalColumnByName("amount");
        this.DESCRIPTION = getInternalColumnByName("description");
        this.ENTITY_TYPE_ENUM = getInternalColumnByName("entity_type_enum");
        this.ENTITY_ID = getInternalColumnByName("entity_id");
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
        this.RUNNING_BALANCE_CALCULATED = getInternalColumnByName("is_running_balance_calculated");
        this.OFFICE_RUNNING_BALANCE = getInternalColumnByName("office_running_balance");
        this.ORGANIZATION_RUNNING_BALANCE = getInternalColumnByName("organization_running_balance");
        this.PAYMENT_DETAIL_ID = getInternalColumnByName("payment_details_id");
        this.SHARE_TRANSACTION_ID = getInternalColumnByName("share_transaction_id");
        this.TRANSACTION_DATE = getInternalColumnByName("transaction_date");
    }

}
