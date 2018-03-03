package com.angkorteam.fintech.ddl;

public interface MImportDocument {

    public static final String NAME = "m_import_document";

    public interface Field {

        public static final String ID = "id";

        public static final String DOCUMENT_ID = "document_id";

        public static final String IMPORT_TIME = "import_time";

        public static final String END_TIME = "end_time";

        public static final String ENTITY_TYPE = "entity_type";

        public static final String COMPLETED = "completed";

        public static final String TOTAL_RECORDS = "total_records";

        public static final String SUCCESS_COUNT = "success_count";

        public static final String FAILURE_COUNT = "failure_count";

        public static final String CREATED_BY_ID = "createdby_id";
    }

}
