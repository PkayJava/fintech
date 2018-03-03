package com.angkorteam.fintech.ddl;

public interface MAccountTransferStandingInstructionsHistory {

    public static final String NAME = "m_account_transfer_standing_instructions_history";

    public interface Field {

        public static final String ID = "id";

        public static final String STANDING_INSTRUCTION_ID = "standing_instruction_id";

        public static final String STATUS = "status";

        public static final String EXECUTION_TIME = "execution_time";

        public static final String AMOUNT = "amount";

        public static final String ERROR_LOG = "error_log";

    }

}
