package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.EXTERNAL_ID = getInternalColumnByName("external_id");
        this.NAME = getInternalColumnByName("name");
    }

}
