package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.CLIENT_ID = getInternalColumnByName("client_id");
        this.FROM_OFFICE_ID = getInternalColumnByName("from_office_id");
        this.TO_OFFICE_ID = getInternalColumnByName("to_office_id");
        this.PROPOSED_TRANSFER_DATE = getInternalColumnByName("proposed_transfer_date");
        this.TRANSFER_TYPE = getInternalColumnByName("transfer_type");
        this.SUBMITTED_ON = getInternalColumnByName("submitted_on");
        this.SUBMITTED_BY = getInternalColumnByName("submitted_by");
    }

}
