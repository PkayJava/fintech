package com.angkorteam.fintech.ddl;

public interface MSavingsAccountCharge {

    public static final String NAME = "m_savings_account_charge";

    public interface Field {

        public static final String ID = "id";

        public static final String SAVINGS_ACCOUNT_ID = "savings_account_id";

        public static final String CHARGE_ID = "charge_id";

        public static final String IS_PENALTY = "is_penalty";

        public static final String CHARGE_TIME_ENUM = "charge_time_enum";

        public static final String CHARGE_DUE_DATE = "charge_due_date";

        public static final String FEE_ON_MONTH = "fee_on_month";

        public static final String FEE_ON_DAY = "fee_on_day";

        public static final String FEE_INTERVAL = "fee_interval";

        public static final String CHARGE_CALCULATION_ENUM = "charge_calculation_enum";

        public static final String CALCULATION_PERCENTAGE = "calculation_percentage";

        public static final String CALCULATION_ON_AMOUNT = "calculation_on_amount";

        public static final String AMOUNT = "amount";

        public static final String AMOUNT_PAID_DERIVED = "amount_paid_derived";

        public static final String AMOUNT_WAIVED_DERIVED = "amount_waived_derived";

        public static final String AMOUNT_WRITTEN_OFF_DERIVED = "amount_writtenoff_derived";

        public static final String AMOUNT_OUTSTANDING_DERIVED = "amount_outstanding_derived";

        public static final String IS_PAID_DERIVED = "is_paid_derived";

        public static final String WAIVED = "waived";

        public static final String IS_ACTIVE = "is_active";

        public static final String INACTIVATED_ON_DATE = "inactivated_on_date";

    }

}
