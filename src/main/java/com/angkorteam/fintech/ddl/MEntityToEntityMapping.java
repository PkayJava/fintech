package com.angkorteam.fintech.ddl;

public interface MEntityToEntityMapping {

    public static final String NAME = "m_entity_to_entity_mapping";

    public interface Field {

        public static final String ID = "id";

        public static final String REL_ID = "rel_id";

        public static final String FROM_ID = "from_id";

        public static final String TO_ID = "to_id";

        public static final String START_DATE = "start_date";

        public static final String END_DATE = "end_date";

    }

}
