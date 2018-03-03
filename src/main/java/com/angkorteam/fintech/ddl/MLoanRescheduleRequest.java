package com.angkorteam.fintech.ddl;

public interface MLoanRescheduleRequest {

    public static final String NAME = "m_loan_reschedule_request";

    public interface Field {

        public static final String ID = "id";

        public static final String LOAN_ID = "loan_id";

        public static final String STATUS_ENUM = "status_enum";

        public static final String RESCHEDULE_FROM_INSTALLMENT = "reschedule_from_installment";

        public static final String RESCHEDULE_FROM_DATE = "reschedule_from_date";

        public static final String RECALCULATE_INTEREST = "recalculate_interest";

        public static final String RESCHEDULE_REASON_CV_ID = "reschedule_reason_cv_id";

        public static final String RESCHEDULE_REASON_COMMENT = "reschedule_reason_comment";

        public static final String SUBMITTED_ON_DATE = "submitted_on_date";

        public static final String SUBMITTED_BY_USER_ID = "submitted_by_user_id";

        public static final String APPROVED_ON_DATE = "approved_on_date";

        public static final String APPROVED_BY_USER_ID = "approved_by_user_id";

        public static final String REJECTED_ON_DATE = "rejected_on_date";

        public static final String REJECTED_BY_USER_ID = "rejected_by_user_id";

    }

}
