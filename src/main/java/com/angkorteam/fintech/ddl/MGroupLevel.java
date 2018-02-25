package com.angkorteam.fintech.ddl;

public interface MGroupLevel {

    public static final String NAME = "m_group_level";

    public interface Field {

        public static final String ID = "id";

        public static final String PARENT_ID = "parent_id";

        public static final String SUPER_PARENT = "super_parent";

        public static final String LEVEL_NAME = "level_name";

        public static final String RECURSABLE = "recursable";

        public static final String CAN_HAVE_CLIENTS = "can_have_clients";

    }

}
