package com.angkorteam.fintech.ddl;

public interface NotificationMapper {

    public static final String NAME = "notification_mapper";

    public interface Field {

        public static final String ID = "id";

        public static final String NOTIFICATION_ID = "notification_id";

        public static final String USER_ID = "user_id";

        public static final String IS_READ = "is_read";

        public static final String CREATED_AT = "created_at";

    }

}
