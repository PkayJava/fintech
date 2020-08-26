package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.CLIENT_ID = getInternalColumnByName("client_id");
        this.MEETING_ID = getInternalColumnByName("meeting_id");
        this.ATTENDANCE_TYPE_ENUM = getInternalColumnByName("attendance_type_enum");
    }

}
