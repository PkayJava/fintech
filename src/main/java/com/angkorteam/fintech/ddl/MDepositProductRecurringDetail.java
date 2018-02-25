package com.angkorteam.fintech.ddl;

public interface MDepositProductRecurringDetail {

    public static final String NAME = "m_deposit_product_recurring_detail";

    public interface Field {

        public static final String ID = "id";

        public static final String SAVINGS_PRODUCT_ID = "savings_product_id";

        public static final String IS_MANDATORY = "is_mandatory";

        public static final String ALLOW_WITHDRAWAL = "allow_withdrawal";

        public static final String ADJUST_ADVANCE_TOWARDS_FUTURE_PAYMENTS = "adjust_advance_towards_future_payments";

    }

}
