package com.angkorteam.fintech.ddl;

public interface NotificationGenerator {

    public static final String NAME = "notification_generator";

    public interface Field {

        public static final String ID = "id";

        public static final String OBJECT_TYPE = "object_type";

        public static final String OBJECT_IDENTIFIER = "object_identifier";

        public static final String ACTION = "action";

        public static final String ACTOR = "actor";

        public static final String IS_SYSTEM_GENERATED = "is_system_generated";

        public static final String NOTIFICATION_CONTENT = "notification_content";

        public static final String CREATED_AT = "created_at";

    }

}
