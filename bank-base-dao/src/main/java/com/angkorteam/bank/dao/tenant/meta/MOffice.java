package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MOffice extends AbstractTable {

    public final Column ID;

    public final Column PARENT_ID;

    public final Column HIERARCHY;

    public final Column EXTERNAL_ID;

    public final Column NAME;

    public final Column OPENING_DATE;

    public static MOffice staticInitialize(DataContext dataContext) {
        return new MOffice(dataContext);
    }

    private MOffice(DataContext dataContext) {
        super(dataContext, "m_office");
        this.ID = getInternalColumnByName("id");
        this.PARENT_ID = getInternalColumnByName("parent_id");
        this.HIERARCHY = getInternalColumnByName("hierarchy");
        this.EXTERNAL_ID = getInternalColumnByName("external_id");
        this.NAME = getInternalColumnByName("name");
        this.OPENING_DATE = getInternalColumnByName("opening_date");
    }

}
