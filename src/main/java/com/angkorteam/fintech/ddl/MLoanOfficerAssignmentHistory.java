package com.angkorteam.fintech.ddl;

public interface MLoanOfficerAssignmentHistory {

    public static final String NAME = "m_loan_officer_assignment_history";

    public interface Field {

        public static final String ID = "id";

        public static final String LOAN_ID = "loan_id";

        public static final String LOAN_OFFICER_ID = "loan_officer_id";

        public static final String START_DATE = "start_date";

        public static final String END_DATE = "end_date";

        public static final String CREATED_BY_ID = "createdby_id";

        public static final String CREATED_DATE = "created_date";

        public static final String LAST_MODIFIED_DATE = "lastmodified_date";

        public static final String LAST_MODIFIED_BY_ID = "lastmodifiedby_id";

    }

}
