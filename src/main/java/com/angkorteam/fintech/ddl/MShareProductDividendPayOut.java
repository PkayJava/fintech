package com.angkorteam.fintech.ddl;

public interface MShareProductDividendPayOut {

    public static final String NAME = "m_share_product_dividend_pay_out";

    public interface Field {

        public static final String ID = "id";

        public static final String PRODUCT_ID = "product_id";

        public static final String AMOUNT = "amount";

        public static final String DIVIDEND_PERIOD_START_DATE = "dividend_period_start_date";

        public static final String DIVIDEND_PERIOD_END_DATE = "dividend_period_end_date";

        public static final String STATUS = "status";

        public static final String CREATED_BY_ID = "createdby_id";

        public static final String CREATED_DATE = "created_date";

        public static final String LAST_MODIFIED_BY_ID = "lastmodifiedby_id";

        public static final String LAST_MODIFIED_DATE = "lastmodified_date";

    }

}
