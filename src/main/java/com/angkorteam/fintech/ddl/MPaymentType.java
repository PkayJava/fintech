package com.angkorteam.fintech.ddl;

public interface MPaymentType {

    public static final String NAME = "m_payment_type";

    public interface Field {

        public static final String ID = "id";

        public static final String VALUE = "value";

        public static final String DESCRIPTION = "description";

        public static final String IS_CASH_PAYMENT = "is_cash_payment";

        public static final String ORDER_POSITION = "order_position";

    }

}
