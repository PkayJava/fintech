package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MHolidayOffice extends AbstractTable {

    public final Column HOLIDAY_ID;

    public final Column OFFICE_ID;

    public static MHolidayOffice staticInitialize(DataContext dataContext) {
        return new MHolidayOffice(dataContext);
    }

    private MHolidayOffice(DataContext dataContext) {
        super(dataContext, "m_holiday_office");
        this.HOLIDAY_ID = getInternalColumnByName("holiday_id");
        this.OFFICE_ID = getInternalColumnByName("office_id");
    }

}
