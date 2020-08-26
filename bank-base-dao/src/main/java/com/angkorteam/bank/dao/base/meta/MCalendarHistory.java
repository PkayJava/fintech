package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MCalendarHistory extends AbstractTable {

    public final Column ID;

    public final Column CALENDAR_ID;

    public final Column TITLE;

    public final Column DESCRIPTION;

    public final Column LOCATION;

    public final Column START_DATE;

    public final Column END_DATE;

    public final Column DURATION;

    public final Column CALENDAR_TYPE_ENUM;

    public final Column REPEATING;

    public final Column RECURRENCE;

    public final Column REMIND_BY_ENUM;

    public final Column FIRST_REMINDER;

    public final Column SECOND_REMINDER;

    public static MCalendarHistory staticInitialize(DataContext dataContext) {
        return new MCalendarHistory(dataContext);
    }

    private MCalendarHistory(DataContext dataContext) {
        super(dataContext, "m_calendar_history");
        this.ID = getInternalColumnByName("id");
        this.CALENDAR_ID = getInternalColumnByName("calendar_id");
        this.TITLE = getInternalColumnByName("title");
        this.DESCRIPTION = getInternalColumnByName("description");
        this.LOCATION = getInternalColumnByName("location");
        this.START_DATE = getInternalColumnByName("start_date");
        this.END_DATE = getInternalColumnByName("end_date");
        this.DURATION = getInternalColumnByName("duration");
        this.CALENDAR_TYPE_ENUM = getInternalColumnByName("calendar_type_enum");
        this.REPEATING = getInternalColumnByName("repeating");
        this.RECURRENCE = getInternalColumnByName("recurrence");
        this.REMIND_BY_ENUM = getInternalColumnByName("remind_by_enum");
        this.FIRST_REMINDER = getInternalColumnByName("first_reminder");
        this.SECOND_REMINDER = getInternalColumnByName("second_reminder");
    }

}
