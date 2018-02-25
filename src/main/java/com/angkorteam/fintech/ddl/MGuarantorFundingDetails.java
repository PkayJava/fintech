package com.angkorteam.fintech.ddl;

public interface MGuarantorFundingDetails {

    public static final String NAME = "m_guarantor_funding_details";

    public interface Field {

        public static final String ID = "id";

        public static final String GUARANTOR_ID = "guarantor_id";

        public static final String ACCOUNT_ASSOCIATIONS_ID = "account_associations_id";

        public static final String AMOUNT = "amount";

        public static final String AMOUNT_RELEASED_DERIVED = "amount_released_derived";

        public static final String AMOUNT_REMAINING_DERIVED = "amount_remaining_derived";

        public static final String AMOUNT_TRANSFERED_DERIVED = "amount_transfered_derived";

        public static final String STATUS_ENUM = "status_enum";

    }

}
