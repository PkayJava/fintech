package com.angkorteam.fintech.ddl;

public interface MEntityRelation {

    public static final String NAME = "m_entity_relation";

    public interface Field {

        public static final String ID = "id";

        public static final String FROM_ENTITY_TYPE = "from_entity_type";

        public static final String TO_ENTITY_TYPE = "to_entity_type";

        public static final String CODE_NAME = "code_name";

    }

}
