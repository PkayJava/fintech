package com.angkorteam.fintech.ddl;

public interface SmsMessagesOutbound {

    public static final String NAME = "sms_messages_outbound";

    public interface Field {

        public static final String ID = "id";

        public static final String GROUP_ID = "group_id";

        public static final String CLIENT_ID = "client_id";

        public static final String STAFF_ID = "staff_id";

        public static final String STATUS_ENUM = "status_enum";

        public static final String MOBILE_NO = "mobile_no";

        public static final String MESSAGE = "message";

        public static final String CAMPAIGN_ID = "campaign_id";

        public static final String EXTERNAL_ID = "external_id";

        public static final String SUBMITTED_ON_DATE = "submittedon_date";

        public static final String DELIVERED_ON_DATE = "delivered_on_date";

        public static final String IS_NOTIFICATION = "is_notification";

    }

}
