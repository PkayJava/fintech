package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MClientTransferDetail extends AbstractTable {

    public final Column ID;

    public final Column CLIENT_ID;

    public final Column FROM_OFFICE_ID;

    public final Column TO_OFFICE_ID;

    public final Column PROPOSED_TRANSFER_DATE;

    public final Column TRANSFER_TYPE;

    public final Column SUBMITTED_ON;

    public final Column SUBMITTED_BY;

    public static MClientTransferDetail staticInitialize(DataContext dataContext) {
        return new MClientTransferDetail(dataContext);
    }

    private MClientTransferDetail(DataContext dataContext) {
        super(dataContext, "m_client_transfer_details");
        this.ID = this.delegate.getColumnByName("id");
        this.CLIENT_ID = this.delegate.getColumnByName("client_id");
        this.FROM_OFFICE_ID = this.delegate.getColumnByName("from_office_id");
        this.TO_OFFICE_ID = this.delegate.getColumnByName("to_office_id");
        this.PROPOSED_TRANSFER_DATE = this.delegate.getColumnByName("proposed_transfer_date");
        this.TRANSFER_TYPE = this.delegate.getColumnByName("transfer_type");
        this.SUBMITTED_ON = this.delegate.getColumnByName("submitted_on");
        this.SUBMITTED_BY = this.delegate.getColumnByName("submitted_by");
    }

}
