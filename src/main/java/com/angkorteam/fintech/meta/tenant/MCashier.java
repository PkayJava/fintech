package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.STAFF_ID = getInternalColumnByName("staff_id");
        this.TELLER_ID = getInternalColumnByName("teller_id");
        this.DESCRIPTION = getInternalColumnByName("description");
        this.START_DATE = getInternalColumnByName("start_date");
        this.END_DATE = getInternalColumnByName("end_date");
        this.START_TIME = getInternalColumnByName("start_time");
        this.END_TIME = getInternalColumnByName("end_time");
        this.FULL_DAY = getInternalColumnByName("full_day");
    }

}
