package com.angkorteam.fintech.ddl;

public interface MClient {

    public static final String NAME = "m_client";

    public interface Field {

        public static final String ID = "id";

        public static final String ACCOUNT_NO = "account_no";

        public static final String EXTERNAL_ID = "external_id";

        public static final String STATUS_ENUM = "status_enum";

        public static final String SUB_STATUS = "sub_status";

        public static final String ACTIVATION_DATE = "activation_date";

        public static final String OFFICE_JOINING_DATE = "office_joining_date";

        public static final String OFFICE_ID = "office_id";

        public static final String TRANSFER_TO_OFFICE_ID = "transfer_to_office_id";

        public static final String STAFF_ID = "staff_id";

        public static final String FIRST_NAME = "firstname";

        public static final String MIDDLE_NAME = "middlename";

        public static final String LAST_NAME = "lastname";

        public static final String FULL_NAME = "fullname";

        public static final String DISPLAY_NAME = "display_name";

        public static final String MOBILE_NO = "mobile_no";

        public static final String IS_STAFF = "is_staff";

        public static final String GENDER_CV_ID = "gender_cv_id";

        public static final String DATE_OF_BIRTH = "date_of_birth";

        public static final String IMAGE_ID = "image_id";

        public static final String CLOSURE_REASON_CV_ID = "closure_reason_cv_id";

        public static final String CLOSED_ON_DATE = "closedon_date";

        public static final String UPDATED_BY = "updated_by";

        public static final String UPDATED_ON = "updated_on";

        public static final String SUBMITTED_ON_DATE = "submittedon_date";

        public static final String ACTIVATED_ON_USER_ID = "activatedon_userid";

        public static final String CLOSED_ON_USER_ID = "closedon_userid";

        public static final String DEFAULT_SAVINGS_PRODUCT = "default_savings_product";

        public static final String DEFAULT_SAVINGS_ACCOUNT = "default_savings_account";

        public static final String CLIENT_TYPE_CV_ID = "client_type_cv_id";

        public static final String CLIENT_CLASSIFICATION_CV_ID = "client_classification_cv_id";

        public static final String REJECT_REASON_CV_ID = "reject_reason_cv_id";

        public static final String REJECTED_ON_DATE = "rejectedon_date";

        public static final String REJECTED_ON_USER_ID = "rejectedon_userid";

        public static final String WITHDRAW_REASON_CV_ID = "withdraw_reason_cv_id";

        public static final String WITHDRAWN_ON_DATE = "withdrawn_on_date";

        public static final String WITHDRAW_ON_USER_ID = "withdraw_on_userid";

        public static final String REACTIVATED_ON_DATE = "reactivated_on_date";

        public static final String REACTIVATED_ON_USER_ID = "reactivated_on_userid";

        public static final String LEGAL_FORM_ENUM = "legal_form_enum";

        public static final String REOPENED_ON_DATE = "reopened_on_date";

        public static final String REOPENED_BY_USER_ID = "reopened_by_userid";

        public static final String EMAIL_ADDRESS = "email_address";

    }

}
