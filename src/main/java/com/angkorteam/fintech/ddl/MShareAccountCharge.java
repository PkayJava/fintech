package com.angkorteam.fintech.ddl;

public interface MShareAccountCharge {

    public static final String NAME = "m_share_account_charge";

    public interface Field {

        public static final String ID = "id";

        public static final String ACCOUNT_ID = "account_id";

        public static final String CHARGE_ID = "charge_id";

        public static final String CHARGE_TIME_ENUM = "charge_time_enum";

        public static final String CHARGE_CALCULATION_ENUM = "charge_calculation_enum";

        public static final String CHARGE_PAYMENT_MODE_ENUM = "charge_payment_mode_enum";

        public static final String CALCULATION_PERCENTAGE = "calculation_percentage";

        public static final String CALCULATION_ON_AMOUNT = "calculation_on_amount";

        public static final String CHARGE_AMOUNT_OR_PERCENTAGE = "charge_amount_or_percentage";

        public static final String AMOUNT = "amount";

        public static final String AMOUNT_PAID_DERIVED = "amount_paid_derived";

        public static final String AMOUNT_WAIVED_DERIVED = "amount_waived_derived";

        public static final String AMOUNT_WRITTEN_OFF_DERIVED = "amount_writtenoff_derived";

        public static final String AMOUNT_OUTSTANDING_DERIVED = "amount_outstanding_derived";

        public static final String IS_PAID_DERIVED = "is_paid_derived";

        public static final String WAIVED = "waived";

        public static final String MIN_CAP = "min_cap";

        public static final String MAX_CAP = "max_cap";

        public static final String IS_ACTIVE = "is_active";

    }

}
