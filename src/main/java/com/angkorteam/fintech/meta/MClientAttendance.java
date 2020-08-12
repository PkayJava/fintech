package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MClientAttendance extends AbstractTable {

    public final Column ID;

    public final Column CLIENT_ID;

    public final Column MEETING_ID;

    public final Column ATTENDANCE_TYPE_ENUM;

    public static MClientAttendance staticInitialize(DataContext dataContext) {
        return new MClientAttendance(dataContext);
    }

    private MClientAttendance(DataContext dataContext) {
        super(dataContext, "m_client_attendance");
        this.ID = this.delegate.getColumnByName("id");
        this.CLIENT_ID = this.delegate.getColumnByName("client_id");
        this.MEETING_ID = this.delegate.getColumnByName("meeting_id");
        this.ATTENDANCE_TYPE_ENUM = this.delegate.getColumnByName("attendance_type_enum");
    }

}
