package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class InteropIdentifier extends AbstractTable {

    public final Column ID;

    public final Column ACCOUNT_ID;

    public final Column TYPE;

    public final Column A_VALUE;

    public final Column SUB_VALUE_OR_TYPE;

    public final Column CREATED_BY;

    public final Column CREATED_ON;

    public final Column MODIFIED_BY;

    public final Column MODIFIED_ON;

    public static InteropIdentifier staticInitialize(DataContext dataContext) {
        return new InteropIdentifier(dataContext);
    }

    private InteropIdentifier(DataContext dataContext) {
        super(dataContext, "interop_identifier");
        this.ID = this.delegate.getColumnByName("id");
        this.ACCOUNT_ID = this.delegate.getColumnByName("account_id");
        this.TYPE = this.delegate.getColumnByName("type");
        this.A_VALUE = this.delegate.getColumnByName("a_value");
        this.SUB_VALUE_OR_TYPE = this.delegate.getColumnByName("sub_value_or_type");
        this.CREATED_BY = this.delegate.getColumnByName("created_by");
        this.CREATED_ON = this.delegate.getColumnByName("created_on");
        this.MODIFIED_BY = this.delegate.getColumnByName("modified_by");
        this.MODIFIED_ON = this.delegate.getColumnByName("modified_on");
    }

}
