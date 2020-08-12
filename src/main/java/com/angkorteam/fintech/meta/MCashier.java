package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MCashier extends AbstractTable {

    public final Column ID;

    public final Column STAFF_ID;

    public final Column TELLER_ID;

    public final Column DESCRIPTION;

    public final Column START_DATE;

    public final Column END_DATE;

    public final Column START_TIME;

    public final Column END_TIME;

    public final Column FULL_DAY;

    public static MCashier staticInitialize(DataContext dataContext) {
        return new MCashier(dataContext);
    }

    private MCashier(DataContext dataContext) {
        super(dataContext, "m_cashiers");
        this.ID = this.delegate.getColumnByName("id");
        this.STAFF_ID = this.delegate.getColumnByName("staff_id");
        this.TELLER_ID = this.delegate.getColumnByName("teller_id");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
        this.START_DATE = this.delegate.getColumnByName("start_date");
        this.END_DATE = this.delegate.getColumnByName("end_date");
        this.START_TIME = this.delegate.getColumnByName("start_time");
        this.END_TIME = this.delegate.getColumnByName("end_time");
        this.FULL_DAY = this.delegate.getColumnByName("full_day");
    }

}
