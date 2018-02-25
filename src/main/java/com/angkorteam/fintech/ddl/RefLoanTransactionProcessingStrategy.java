package com.angkorteam.fintech.ddl;

public interface RefLoanTransactionProcessingStrategy {

    public static final String NAME = "ref_loan_transaction_processing_strategy";

    public interface Field {

        public static final String ID = "id";

        public static final String CODE = "code";

        public static final String NAME = "name";
        
        public static final String SORT_ORDER = "sort_order";

    }

}
