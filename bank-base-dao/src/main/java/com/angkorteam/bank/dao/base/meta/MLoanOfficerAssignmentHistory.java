package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanOfficerAssignmentHistory extends AbstractTable {

    public final Column ID;

    public final Column LOAN_ID;

    public final Column LOAN_OFFICER_ID;

    public final Column START_DATE;

    public final Column END_DATE;

    public final Column CREATED_BY_ID;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_DATE;

    public final Column LAST_MODIFIED_BY_ID;

    public static MLoanOfficerAssignmentHistory staticInitialize(DataContext dataContext) {
        return new MLoanOfficerAssignmentHistory(dataContext);
    }

    private MLoanOfficerAssignmentHistory(DataContext dataContext) {
        super(dataContext, "m_loan_officer_assignment_history");
        this.ID = getInternalColumnByName("id");
        this.LOAN_ID = getInternalColumnByName("loan_id");
        this.LOAN_OFFICER_ID = getInternalColumnByName("loan_officer_id");
        this.START_DATE = getInternalColumnByName("start_date");
        this.END_DATE = getInternalColumnByName("end_date");
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
    }

}
