package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MHoliday extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column FROM_DATE;

    public final Column TO_DATE;

    public final Column REPAYMENT_RESCHEDULED_TO;

    public final Column STATUS_ENUM;

    public final Column PROCESSED;

    public final Column DESCRIPTION;

    public final Column RESCHEDULING_TYPE;

    public static MHoliday staticInitialize(DataContext dataContext) {
        return new MHoliday(dataContext);
    }

    private MHoliday(DataContext dataContext) {
        super(dataContext, "m_holiday");
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
        this.FROM_DATE = getInternalColumnByName("from_date");
        this.TO_DATE = getInternalColumnByName("to_date");
        this.REPAYMENT_RESCHEDULED_TO = getInternalColumnByName("repayments_rescheduled_to");
        this.STATUS_ENUM = getInternalColumnByName("status_enum");
        this.PROCESSED = getInternalColumnByName("processed");
        this.DESCRIPTION = getInternalColumnByName("description");
        this.RESCHEDULING_TYPE = getInternalColumnByName("rescheduling_type");
    }

}
