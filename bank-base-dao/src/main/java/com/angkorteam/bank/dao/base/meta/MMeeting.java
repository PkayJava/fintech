package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.CALENDAR_INSTANCE_ID = getInternalColumnByName("calendar_instance_id");
        this.MEETING_DATE = getInternalColumnByName("meeting_date");
    }

}
