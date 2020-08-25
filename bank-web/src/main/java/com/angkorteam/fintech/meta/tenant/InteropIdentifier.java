package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.ACCOUNT_ID = getInternalColumnByName("account_id");
        this.TYPE = getInternalColumnByName("type");
        this.A_VALUE = getInternalColumnByName("a_value");
        this.SUB_VALUE_OR_TYPE = getInternalColumnByName("sub_value_or_type");
        this.CREATED_BY = getInternalColumnByName("created_by");
        this.CREATED_ON = getInternalColumnByName("created_on");
        this.MODIFIED_BY = getInternalColumnByName("modified_by");
        this.MODIFIED_ON = getInternalColumnByName("modified_on");
    }

}
