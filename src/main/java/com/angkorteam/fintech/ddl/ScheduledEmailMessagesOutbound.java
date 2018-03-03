package com.angkorteam.fintech.ddl;

public interface ScheduledEmailMessagesOutbound {

    public static final String NAME = "scheduled_email_messages_outbound";

    public interface Field {

        public static final String ID = "id";

        public static final String GROUP_ID = "group_id";

        public static final String CLIENT_ID = "client_id";

        public static final String STAFF_ID = "staff_id";

        public static final String EMAIL_CAMPAIGN_ID = "email_campaign_id";

        public static final String STATUS_ENUM = "status_enum";

        public static final String EMAIL_ADDRESS = "email_address";

        public static final String EMAIL_SUBJECT = "email_subject";

        public static final String MESSAGE = "message";

        public static final String CAMPAIGN_NAME = "campaign_name";

        public static final String SUBMITTED_ON_DATE = "submittedon_date";

        public static final String ERROR_MESSAGE = "error_message";

    }

}
