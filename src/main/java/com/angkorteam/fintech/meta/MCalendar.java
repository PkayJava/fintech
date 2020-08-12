package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MCalendar extends AbstractTable {

    public final Column ID;

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

    public final Column CREATED_BY_ID;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_DATE;

    public final Column MEETING_TIME;

    public static MCalendar staticInitialize(DataContext dataContext) {
        return new MCalendar(dataContext);
    }

    private MCalendar(DataContext dataContext) {
        super(dataContext, "m_calendar");
        this.ID = this.delegate.getColumnByName("id");
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
        this.CREATED_BY_ID = this.delegate.getColumnByName("createdby_id");
        this.LAST_MODIFIED_BY_ID = this.delegate.getColumnByName("lastmodifiedby_id");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
        this.LAST_MODIFIED_DATE = this.delegate.getColumnByName("lastmodified_date");
        this.MEETING_TIME = this.delegate.getColumnByName("meeting_time");
    }

}
