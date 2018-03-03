package com.angkorteam.fintech.ddl;

public interface MFieldConfiguration {

    public static final String NAME = "m_field_configuration";

    public interface Field {

        public static final String ID = "id";

        public static final String ENTITY = "entity";

        public static final String SUB_ENTITY = "subentity";

        public static final String FIELD = "field";

        public static final String IS_ENABLED = "is_enabled";

        public static final String IS_MANDATORY = "is_mandatory";

        public static final String VALIDATION_REGEX = "validation_regex";

    }

}
