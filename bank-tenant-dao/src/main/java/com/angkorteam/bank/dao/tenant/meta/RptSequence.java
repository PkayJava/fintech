package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class RptSequence extends AbstractTable {

    public final Column ID;

    public static RptSequence staticInitialize(DataContext dataContext) {
        return new RptSequence(dataContext);
    }

    private RptSequence(DataContext dataContext) {
        super(dataContext, "rpt_sequence");
        this.ID = getInternalColumnByName("id");
    }

}
