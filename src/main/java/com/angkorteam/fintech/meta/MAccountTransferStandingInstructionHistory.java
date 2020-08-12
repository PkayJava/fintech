package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MAccountTransferStandingInstructionHistory extends AbstractTable {

    public final Column ID;

    public final Column STATUS;

    public final Column AMOUNT;

    public final Column STANDING_INSTRUCTION_ID;

    public final Column EXECUTION_TIME;

    public final Column ERROR_LOG;

    public static MAccountTransferStandingInstructionHistory staticInitialize(DataContext dataContext) {
        return new MAccountTransferStandingInstructionHistory(dataContext);
    }

    private MAccountTransferStandingInstructionHistory(DataContext dataContext) {
        super(dataContext, "m_account_transfer_standing_instructions_history");
        this.ID = this.delegate.getColumnByName("id");
        this.STATUS = this.delegate.getColumnByName("status");
        this.AMOUNT = this.delegate.getColumnByName("amount");
        this.STANDING_INSTRUCTION_ID = this.delegate.getColumnByName("standing_instruction_id");
        this.EXECUTION_TIME = this.delegate.getColumnByName("execution_time");
        this.ERROR_LOG = this.delegate.getColumnByName("error_log");
    }

}
