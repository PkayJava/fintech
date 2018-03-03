package com.angkorteam.fintech.ddl;

public interface MDocument {

    public static final String NAME = "m_document";

    public interface Field {

        public static final String ID = "id";

        public static final String PARENT_ENTITY_TYPE = "parent_entity_type";

        public static final String PARENT_ENTITY_ID = "parent_entity_id";

        public static final String NAME = "name";

        public static final String FILE_NAME = "file_name";

        public static final String SIZE = "size";

        public static final String TYPE = "type";

        public static final String DESCRIPTION = "description";

        public static final String LOCATION = "location";

        public static final String STORAGE_TYPE_ENUM = "storage_type_enum";

    }

}
