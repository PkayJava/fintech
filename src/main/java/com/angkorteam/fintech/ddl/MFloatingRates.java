package com.angkorteam.fintech.ddl;

public interface MFloatingRates {

    public static final String NAME = "m_floating_rates";

    public interface Field {

        public static final String ID = "id";

        public static final String NAME = "name";

        public static final String IS_BASE_LENDING_RATE = "is_base_lending_rate";

        public static final String IS_ACTIVE = "is_active";

        public static final String CREATED_BY_ID = "createdby_id";

        public static final String CREATED_DATE = "created_date";

        public static final String LAST_MODIFIED_BY_ID = "lastmodifiedby_id";

        public static final String LAST_MODIFIED_DATE = "lastmodified_date";

    }

}
