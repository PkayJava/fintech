package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.NAME = this.delegate.getColumnByName("name");
        this.ACCOUNT_TRANSFER_DETAIL_ID = this.delegate.getColumnByName("account_transfer_details_id");
        this.PRIORITY = this.delegate.getColumnByName("priority");
        this.STATUS = this.delegate.getColumnByName("status");
        this.INSTRUCTION_TYPE = this.delegate.getColumnByName("instruction_type");
        this.AMOUNT = this.delegate.getColumnByName("amount");
        this.VALID_FROM = this.delegate.getColumnByName("valid_from");
        this.VALID_TILL = this.delegate.getColumnByName("valid_till");
        this.RECURRENCE_TYPE = this.delegate.getColumnByName("recurrence_type");
        this.RECURRENCE_FREQUENCY = this.delegate.getColumnByName("recurrence_frequency");
        this.RECURRENCE_INTERVAL = this.delegate.getColumnByName("recurrence_interval");
        this.RECURRENCE_ON_DAY = this.delegate.getColumnByName("recurrence_on_day");
        this.RECURRENCE_ON_MONTH = this.delegate.getColumnByName("recurrence_on_month");
        this.LAST_RUN_DATE = this.delegate.getColumnByName("last_run_date");
    }

}
