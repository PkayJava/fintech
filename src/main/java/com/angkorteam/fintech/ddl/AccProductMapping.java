package com.angkorteam.fintech.ddl;

public interface AccProductMapping {

    public static final String NAME = "acc_product_mapping";

    public interface Field {

        public static final String ID = "id";

        public static final String GL_ACCOUNT_ID = "gl_account_id";

        public static final String PRODUCT_ID = "product_id";

        public static final String PRODUCT_TYPE = "product_type";

        public static final String PAYMENT_TYPE = "payment_type";

        public static final String CHARGE_ID = "charge_id";

        public static final String FINANCIAL_ACCOUNT_TYPE = "financial_account_type";

    }

}
