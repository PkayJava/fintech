package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.PARENT_ID = this.delegate.getColumnByName("parent_id");
        this.HIERARCHY = this.delegate.getColumnByName("hierarchy");
        this.EXTERNAL_ID = this.delegate.getColumnByName("external_id");
        this.NAME = this.delegate.getColumnByName("name");
        this.OPENING_DATE = this.delegate.getColumnByName("opening_date");
    }

}
