package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MWorkingDay extends AbstractTable {

    public final Column ID;

    public final Column RECURRENCE;

    public final Column REPAYMENT_RESCHEDULING_ENUM;

    public final Column EXTEND_TERM_DAILY_REPAYMENT;

    public final Column EXTEND_TERM_HOLIDAY_REPAYMENT;

    public static MWorkingDay staticInitialize(DataContext dataContext) {
        return new MWorkingDay(dataContext);
    }

    private MWorkingDay(DataContext dataContext) {
        super(dataContext, "m_working_days");
        this.ID = getInternalColumnByName("id");
        this.RECURRENCE = getInternalColumnByName("recurrence");
        this.REPAYMENT_RESCHEDULING_ENUM = getInternalColumnByName("repayment_rescheduling_enum");
        this.EXTEND_TERM_DAILY_REPAYMENT = getInternalColumnByName("extend_term_daily_repayments");
        this.EXTEND_TERM_HOLIDAY_REPAYMENT = getInternalColumnByName("extend_term_holiday_repayment");
    }

}
