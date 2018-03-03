package com.angkorteam.fintech.ddl;

public interface MClientChargePaidBy {

    public static final String NAME = "m_client_charge_paid_by";

    public interface Field {

        public static final String ID = "id";

        public static final String CLIENT_TRANSACTION_ID = "client_transaction_id";

        public static final String CLIENT_CHARGE_ID = "client_charge_id";

        public static final String AMOUNT = "amount";

    }

}
