package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.NAME = this.delegate.getColumnByName("name");
        this.FROM_DATE = this.delegate.getColumnByName("from_date");
        this.TO_DATE = this.delegate.getColumnByName("to_date");
        this.REPAYMENT_RESCHEDULED_TO = this.delegate.getColumnByName("repayments_rescheduled_to");
        this.STATUS_ENUM = this.delegate.getColumnByName("status_enum");
        this.PROCESSED = this.delegate.getColumnByName("processed");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
        this.RESCHEDULING_TYPE = this.delegate.getColumnByName("rescheduling_type");
    }

}
