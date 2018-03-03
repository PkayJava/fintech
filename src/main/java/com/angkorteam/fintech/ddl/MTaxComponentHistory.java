package com.angkorteam.fintech.ddl;

public interface MTaxComponentHistory {

    public static final String NAME = "m_tax_component_history";

    public interface Field {

        public static final String ID = "id";

        public static final String TAX_COMPONENT_ID = "tax_component_id";

        public static final String PERCENTAGE = "percentage";

        public static final String START_DATE = "start_date";

        public static final String END_DATE = "end_date";

        public static final String CREATED_BY_ID = "createdby_id";

        public static final String CREATED_DATE = "created_date";

        public static final String LAST_MODIFIED_BY_ID = "lastmodifiedby_id";

        public static final String LAST_MODIFIED_DATE = "lastmodified_date";

    }

}
