package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.STATUS = getInternalColumnByName("status");
        this.AMOUNT = getInternalColumnByName("amount");
        this.STANDING_INSTRUCTION_ID = getInternalColumnByName("standing_instruction_id");
        this.EXECUTION_TIME = getInternalColumnByName("execution_time");
        this.ERROR_LOG = getInternalColumnByName("error_log");
    }

}
