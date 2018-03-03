package com.angkorteam.fintech.ddl;

public interface MHookRegisteredEvents {

    public static final String NAME = "m_hook_registered_events";

    public interface Field {

        public static final String ID = "id";

        public static final String HOOK_ID = "hook_id";

        public static final String ENTITY_NAME = "entity_name";

        public static final String ACTION_NAME = "action_name";

    }

}
