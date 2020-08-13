package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MMeeting extends AbstractTable {

    public final Column ID;

    public final Column CALENDAR_INSTANCE_ID;

    public final Column MEETING_DATE;

    public static MMeeting staticInitialize(DataContext dataContext) {
        return new MMeeting(dataContext);
    }

    private MMeeting(DataContext dataContext) {
        super(dataContext, "m_meeting");
        this.ID = this.delegate.getColumnByName("id");
        this.CALENDAR_INSTANCE_ID = this.delegate.getColumnByName("calendar_instance_id");
        this.MEETING_DATE = this.delegate.getColumnByName("meeting_date");
    }

}
