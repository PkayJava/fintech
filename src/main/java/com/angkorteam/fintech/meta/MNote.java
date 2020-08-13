package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MNote extends AbstractTable {

    public final Column ID;

    public final Column CLIENT_ID;

    public final Column GROUP_ID;

    public final Column LOAN_ID;

    public final Column LOAN_TRANSACTION_ID;

    public final Column SAVING_ACCOUNT_ID;

    public final Column SAVING_ACCOUNT_TRANSACTION_ID;

    public final Column SHARE_ACCOUNT_ID;

    public final Column NOTE_TYPE_ENUM;

    public final Column NOTE;

    public final Column CREATED_DATE;

    public final Column CREATED_BY_ID;

    public final Column LAST_MODIFIED_DATE;

    public final Column LAST_MODIFIED_BY_ID;

    public static MNote staticInitialize(DataContext dataContext) {
        return new MNote(dataContext);
    }

    private MNote(DataContext dataContext) {
        super(dataContext, "m_note");
        this.ID = this.delegate.getColumnByName("id");
        this.CLIENT_ID = this.delegate.getColumnByName("client_id");
        this.GROUP_ID = this.delegate.getColumnByName("group_id");
        this.LOAN_ID = this.delegate.getColumnByName("loan_id");
        this.LOAN_TRANSACTION_ID = this.delegate.getColumnByName("loan_transaction_id");
        this.SAVING_ACCOUNT_ID = this.delegate.getColumnByName("savings_account_id");
        this.SAVING_ACCOUNT_TRANSACTION_ID = this.delegate.getColumnByName("savings_account_transaction_id");
        this.SHARE_ACCOUNT_ID = this.delegate.getColumnByName("share_account_id");
        this.NOTE_TYPE_ENUM = this.delegate.getColumnByName("note_type_enum");
        this.NOTE = this.delegate.getColumnByName("note");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
        this.CREATED_BY_ID = this.delegate.getColumnByName("createdby_id");
        this.LAST_MODIFIED_DATE = this.delegate.getColumnByName("lastmodified_date");
        this.LAST_MODIFIED_BY_ID = this.delegate.getColumnByName("lastmodifiedby_id");
    }

}
