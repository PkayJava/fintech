package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.FROM_OFFICE_ID = getInternalColumnByName("from_office_id");
        this.TO_OFFICE_ID = getInternalColumnByName("to_office_id");
        this.FROM_CLIENT_ID = getInternalColumnByName("from_client_id");
        this.TO_CLIENT_ID = getInternalColumnByName("to_client_id");
        this.FROM_SAVING_ACCOUNT_ID = getInternalColumnByName("from_savings_account_id");
        this.TO_SAVING_ACCOUNT_ID = getInternalColumnByName("to_savings_account_id");
        this.FROM_LOAN_ACCOUNT_ID = getInternalColumnByName("from_loan_account_id");
        this.TO_LOAN_ACCOUNT_ID = getInternalColumnByName("to_loan_account_id");
        this.TRANSFER_TYPE = getInternalColumnByName("transfer_type");
    }

}
