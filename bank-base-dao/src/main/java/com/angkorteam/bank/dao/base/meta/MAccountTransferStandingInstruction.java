package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MAccountTransferStandingInstruction extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column ACCOUNT_TRANSFER_DETAIL_ID;

    public final Column PRIORITY;

    public final Column STATUS;

    public final Column INSTRUCTION_TYPE;

    public final Column AMOUNT;

    public final Column VALID_FROM;

    public final Column VALID_TILL;

    public final Column RECURRENCE_TYPE;

    public final Column RECURRENCE_FREQUENCY;

    public final Column RECURRENCE_INTERVAL;

    public final Column RECURRENCE_ON_DAY;

    public final Column RECURRENCE_ON_MONTH;

    public final Column LAST_RUN_DATE;

    public static MAccountTransferStandingInstruction staticInitialize(DataContext dataContext) {
        return new MAccountTransferStandingInstruction(dataContext);
    }

    private MAccountTransferStandingInstruction(DataContext dataContext) {
        super(dataContext, "m_account_transfer_standing_instructions");
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
        this.ACCOUNT_TRANSFER_DETAIL_ID = getInternalColumnByName("account_transfer_details_id");
        this.PRIORITY = getInternalColumnByName("priority");
        this.STATUS = getInternalColumnByName("status");
        this.INSTRUCTION_TYPE = getInternalColumnByName("instruction_type");
        this.AMOUNT = getInternalColumnByName("amount");
        this.VALID_FROM = getInternalColumnByName("valid_from");
        this.VALID_TILL = getInternalColumnByName("valid_till");
        this.RECURRENCE_TYPE = getInternalColumnByName("recurrence_type");
        this.RECURRENCE_FREQUENCY = getInternalColumnByName("recurrence_frequency");
        this.RECURRENCE_INTERVAL = getInternalColumnByName("recurrence_interval");
        this.RECURRENCE_ON_DAY = getInternalColumnByName("recurrence_on_day");
        this.RECURRENCE_ON_MONTH = getInternalColumnByName("recurrence_on_month");
        this.LAST_RUN_DATE = getInternalColumnByName("last_run_date");
    }

}
