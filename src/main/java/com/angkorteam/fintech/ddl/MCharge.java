package com.angkorteam.fintech.ddl;

public interface MCharge {

    public static final String NAME = "m_charge";

    public interface Field {

        public static final String ID = "id";

        public static final String NAME = "name";

        public static final String CURRENCY_CODE = "currency_code";

        public static final String CHARGE_APPLIES_TO_ENUM = "charge_applies_to_enum";

        public static final String CHARGE_TIME_ENUM = "charge_time_enum";

        public static final String CHARGE_CALCULATION_ENUM = "charge_calculation_enum";

        public static final String CHARGE_PAYMENT_MODE_ENUM = "charge_payment_mode_enum";

        public static final String AMOUNT = "amount";

        public static final String FEE_ON_DAY = "fee_on_day";

        public static final String FEE_INTERVAL = "fee_interval";

        public static final String FEE_ON_MONTH = "fee_on_month";

        public static final String IS_PENALTY = "is_penalty";

        public static final String IS_ACTIVE = "is_active";

        public static final String IS_DELETED = "is_deleted";

        public static final String MIN_CAP = "min_cap";

        public static final String MAX_CAP = "max_cap";

        public static final String FEE_FREQUENCY = "fee_frequency";

        public static final String INCOME_OR_LIABILITY_ACCOUNT_ID = "income_or_liability_account_id";

        public static final String TAX_GROUP_ID = "tax_group_id";

    }

}
