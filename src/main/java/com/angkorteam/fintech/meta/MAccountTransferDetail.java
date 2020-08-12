package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MAccountTransferDetail extends AbstractTable {

    public final Column ID;

    public final Column FROM_OFFICE_ID;

    public final Column TO_OFFICE_ID;

    public final Column FROM_CLIENT_ID;

    public final Column TO_CLIENT_ID;

    public final Column FROM_SAVING_ACCOUNT_ID;

    public final Column TO_SAVING_ACCOUNT_ID;

    public final Column FROM_LOAN_ACCOUNT_ID;

    public final Column TO_LOAN_ACCOUNT_ID;

    public final Column TRANSFER_TYPE;

    public static MAccountTransferDetail staticInitialize(DataContext dataContext) {
        return new MAccountTransferDetail(dataContext);
    }

    private MAccountTransferDetail(DataContext dataContext) {
        super(dataContext, "m_account_transfer_details");
        this.ID = this.delegate.getColumnByName("id");
        this.FROM_OFFICE_ID = this.delegate.getColumnByName("from_office_id");
        this.TO_OFFICE_ID = this.delegate.getColumnByName("to_office_id");
        this.FROM_CLIENT_ID = this.delegate.getColumnByName("from_client_id");
        this.TO_CLIENT_ID = this.delegate.getColumnByName("to_client_id");
        this.FROM_SAVING_ACCOUNT_ID = this.delegate.getColumnByName("from_savings_account_id");
        this.TO_SAVING_ACCOUNT_ID = this.delegate.getColumnByName("to_savings_account_id");
        this.FROM_LOAN_ACCOUNT_ID = this.delegate.getColumnByName("from_loan_account_id");
        this.TO_LOAN_ACCOUNT_ID = this.delegate.getColumnByName("to_loan_account_id");
        this.TRANSFER_TYPE = this.delegate.getColumnByName("transfer_type");
    }

}
