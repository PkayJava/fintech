package com.angkorteam.fintech.ddl;

public interface MGuarantorTransaction {

    public static final String NAME = "m_guarantor_transaction";

    public interface Field {

        public static final String ID = "id";

        public static final String GUARANTOR_FUND_DETAIL_ID = "guarantor_fund_detail_id";

        public static final String LOAN_TRANSACTION_ID = "loan_transaction_id";

        public static final String DEPOSIT_ON_HOLD_TRANSACTION_ID = "deposit_on_hold_transaction_id";

        public static final String IS_REVERSED = "is_reversed";

    }

}
