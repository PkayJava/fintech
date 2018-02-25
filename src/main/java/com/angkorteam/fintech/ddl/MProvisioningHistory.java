package com.angkorteam.fintech.ddl;

public interface MProvisioningHistory {

    public static final String NAME = "m_provisioning_history";

    public interface Field {

        public static final String ID = "id";

        public static final String JOURNAL_ENTRY_CREATED = "journal_entry_created";

        public static final String CREATED_BY_ID = "createdby_id";

        public static final String CREATED_DATE = "created_date";

        public static final String LAST_MODIFIED_BY_ID = "lastmodifiedby_id";

        public static final String LAST_MODIFIED_DATE = "lastmodified_date";

    }

}
