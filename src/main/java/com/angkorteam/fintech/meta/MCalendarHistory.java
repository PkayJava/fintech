package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.CALENDAR_ID = this.delegate.getColumnByName("calendar_id");
        this.TITLE = this.delegate.getColumnByName("title");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
        this.LOCATION = this.delegate.getColumnByName("location");
        this.START_DATE = this.delegate.getColumnByName("start_date");
        this.END_DATE = this.delegate.getColumnByName("end_date");
        this.DURATION = this.delegate.getColumnByName("duration");
        this.CALENDAR_TYPE_ENUM = this.delegate.getColumnByName("calendar_type_enum");
        this.REPEATING = this.delegate.getColumnByName("repeating");
        this.RECURRENCE = this.delegate.getColumnByName("recurrence");
        this.REMIND_BY_ENUM = this.delegate.getColumnByName("remind_by_enum");
        this.FIRST_REMINDER = this.delegate.getColumnByName("first_reminder");
        this.SECOND_REMINDER = this.delegate.getColumnByName("second_reminder");
    }

}
