package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
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
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
        this.MEETING_TIME = getInternalColumnByName("meeting_time");
    }

}
