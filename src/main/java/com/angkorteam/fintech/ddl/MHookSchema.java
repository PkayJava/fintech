package com.angkorteam.fintech.ddl;

public interface MHookSchema {

    public static final String NAME = "m_hook_schema";

    public interface Field {

        public static final String ID = "id";

        public static final String HOOK_TEMPLATE_ID = "hook_template_id";

        public static final String FIELD_TYPE = "field_type";

        public static final String FIELD_NAME = "field_name";

        public static final String PLACE_HOLDER = "placeholder";

        public static final String OPTIONAL = "optional";

    }

}
