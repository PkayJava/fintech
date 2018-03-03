package com.angkorteam.fintech.ddl;

public interface MAddress {

    public static final String NAME = "m_address";

    public interface Field {

        public static final String ID = "id";

        public static final String STREET = "street";

        public static final String ADDRESS_LINE_1 = "address_line_1";

        public static final String ADDRESS_LINE_2 = "address_line_2";

        public static final String ADDRESS_LINE_3 = "address_line_3";

        public static final String TOWN_VILLAGE = "town_village";

        public static final String CITY = "city";

        public static final String COUNTY_DISTRICT = "county_district";

        public static final String STATE_PROVINCE_ID = "state_province_id";

        public static final String COUNTRY_ID = "country_id";

        public static final String POSTAL_CODE = "postal_code";

        public static final String LATITUDE = "latitude";

        public static final String LONGITUDE = "longitude";

        public static final String CREATED_BY = "created_by";

        public static final String CREATED_ON = "created_on";

        public static final String UPDATED_BY = "updated_by";

        public static final String UPDATED_ON = "updated_on";

    }

}
