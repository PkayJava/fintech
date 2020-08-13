package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.LOAN_ID = this.delegate.getColumnByName("loan_id");
        this.LOAN_OFFICER_ID = this.delegate.getColumnByName("loan_officer_id");
        this.START_DATE = this.delegate.getColumnByName("start_date");
        this.END_DATE = this.delegate.getColumnByName("end_date");
        this.CREATED_BY_ID = this.delegate.getColumnByName("createdby_id");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
        this.LAST_MODIFIED_DATE = this.delegate.getColumnByName("lastmodified_date");
        this.LAST_MODIFIED_BY_ID = this.delegate.getColumnByName("lastmodifiedby_id");
    }

}
