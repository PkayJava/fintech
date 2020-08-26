package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class SchedulerDetail extends AbstractTable {

    public final Column ID;

    public final Column SUSPENDED;

    public final Column EXECUTE_MISFIRED_JOB;

    public final Column RESET_SCHEDULER_ON_BOOTUP;

    public static SchedulerDetail staticInitialize(DataContext dataContext) {
        return new SchedulerDetail(dataContext);
    }

    private SchedulerDetail(DataContext dataContext) {
        super(dataContext, "scheduler_detail");
        this.ID = getInternalColumnByName("id");
        this.SUSPENDED = getInternalColumnByName("is_suspended");
        this.EXECUTE_MISFIRED_JOB = getInternalColumnByName("execute_misfired_jobs");
        this.RESET_SCHEDULER_ON_BOOTUP = getInternalColumnByName("reset_scheduler_on_bootup");
    }

}
