package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MStaffAssignmentHistory extends AbstractTable {

    public final Column ID;

    public final Column CENTRE_ID;

    public final Column STAFF_ID;

    public final Column START_DATE;

    public final Column END_DATE;

    public final Column CREATED_BY_ID;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_DATE;

    public final Column LAST_MODIFIED_BY_ID;

    public static MStaffAssignmentHistory staticInitialize(DataContext dataContext) {
        return new MStaffAssignmentHistory(dataContext);
    }

    private MStaffAssignmentHistory(DataContext dataContext) {
        super(dataContext, "m_staff_assignment_history");
        this.ID = getInternalColumnByName("id");
        this.CENTRE_ID = getInternalColumnByName("centre_id");
        this.STAFF_ID = getInternalColumnByName("staff_id");
        this.START_DATE = getInternalColumnByName("start_date");
        this.END_DATE = getInternalColumnByName("end_date");
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
    }

}
