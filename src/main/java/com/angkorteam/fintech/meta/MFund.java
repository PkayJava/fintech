package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MFund extends AbstractTable {

    public final Column ID;

    public final Column EXTERNAL_ID;

    public final Column NAME;

    public static MFund staticInitialize(DataContext dataContext) {
        return new MFund(dataContext);
    }

    private MFund(DataContext dataContext) {
        super(dataContext, "m_fund");
        this.ID = this.delegate.getColumnByName("id");
        this.EXTERNAL_ID = this.delegate.getColumnByName("external_id");
        this.NAME = this.delegate.getColumnByName("name");
    }

}
