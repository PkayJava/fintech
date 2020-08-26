package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MClientIdentifier extends AbstractTable {

    public final Column ID;

    public final Column CLIENT_ID;

    public final Column DOCUMENT_TYPE_ID;

    public final Column DOCUMENT_KEY;

    public final Column STATUS;

    public final Column ACTIVE;

    public final Column DESCRIPTION;

    public final Column CREATED_BY_ID;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_DATE;

    public static MClientIdentifier staticInitialize(DataContext dataContext) {
        return new MClientIdentifier(dataContext);
    }

    private MClientIdentifier(DataContext dataContext) {
        super(dataContext, "m_client_identifier");
        this.ID = getInternalColumnByName("id");
        this.CLIENT_ID = getInternalColumnByName("client_id");
        this.DOCUMENT_TYPE_ID = getInternalColumnByName("document_type_id");
        this.DOCUMENT_KEY = getInternalColumnByName("document_key");
        this.STATUS = getInternalColumnByName("status");
        this.ACTIVE = getInternalColumnByName("active");
        this.DESCRIPTION = getInternalColumnByName("description");
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
    }

}
