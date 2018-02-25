package com.angkorteam.fintech.ddl;

public interface MDepositAccountRecurringDetail {

    public static final String NAME = "m_deposit_account_recurring_detail";

    public interface Field {

        public static final String ID = "id";

        public static final String SAVINGS_ACCOUNT_ID = "savings_account_id";

        public static final String MANDATORY_RECOMMENDED_DEPOSIT_AMOUNT = "mandatory_recommended_deposit_amount";

        public static final String IS_MANDATORY = "is_mandatory";

        public static final String ALLOW_WITHDRAWAL = "allow_withdrawal";

        public static final String ADJUST_ADVANCE_TOWARDS_FUTURE_PAYMENTS = "adjust_advance_towards_future_payments";

        public static final String IS_CALENDAR_INHERITED = "is_calendar_inherited";

        public static final String TOTAL_OVERDUE_AMOUNT = "total_overdue_amount";

        public static final String NO_OF_OVERDUE_INSTALLMENTS = "no_of_overdue_installments";

    }

}
