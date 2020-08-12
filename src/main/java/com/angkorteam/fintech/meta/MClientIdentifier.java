package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.CLIENT_ID = this.delegate.getColumnByName("client_id");
        this.DOCUMENT_TYPE_ID = this.delegate.getColumnByName("document_type_id");
        this.DOCUMENT_KEY = this.delegate.getColumnByName("document_key");
        this.STATUS = this.delegate.getColumnByName("status");
        this.ACTIVE = this.delegate.getColumnByName("active");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
        this.CREATED_BY_ID = this.delegate.getColumnByName("createdby_id");
        this.LAST_MODIFIED_BY_ID = this.delegate.getColumnByName("lastmodifiedby_id");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
        this.LAST_MODIFIED_DATE = this.delegate.getColumnByName("lastmodified_date");
    }

}
